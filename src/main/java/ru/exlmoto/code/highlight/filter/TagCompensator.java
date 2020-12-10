package ru.exlmoto.code.highlight.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Component
public class TagCompensator {
	private final Logger log = LoggerFactory.getLogger(TagCompensator.class);

	private final String TAG_START_MARKER = "\">";
	private final String TAG_START_CHUNK  = "<span";
	private final String TAG_END_MARKER   = "</";
	private final String TAG_END_CHUNK    = "span>";

	private final Stack<String> tagStack;
	private final List<String> tagBuffer;

	private abstract class TagWorker {
		protected void stepTagStack(String line) {
			if (line.contains(TAG_START_MARKER)) {
				String[] tokens = line.split(TAG_START_MARKER);
				for (String token : tokens) {
					if (token.contains(TAG_START_CHUNK)) {
						tagStack.push(token.substring(token.indexOf(TAG_START_CHUNK)) + TAG_START_MARKER);
					}
				}
			}
			if (line.contains(TAG_END_MARKER)) {
				String[] tokens = line.split(TAG_END_MARKER);
				for (String token : tokens) {
					if (token.contains(TAG_END_CHUNK)) {
						tagStack.pop();
					}
				}
			}
		}

		protected void stepTagBuffer(StringBuilder res) {
			if (!tagBuffer.isEmpty()) {
				for (String tag : tagBuffer) {
					res.append(tag);
				}
				tagBuffer.clear();
			}
		}

		protected void fillHtmlChunk(String line, StringBuilder res) {
			final int size = tagStack.size();
			res.append(line);
			if (size > 0) {
				for (int i = 0; i < size; ++i) {
					res.append(TAG_END_MARKER + TAG_END_CHUNK);
				}
			}
			res.append("\n");
		}

		protected boolean checkHtmlTags() {
			return tagStack.isEmpty();
		}

		protected void fillTagBuffer() {
			tagBuffer.addAll(tagStack);
		}

		public abstract void doWork(String line, StringBuilder res);
	}

	private class TagFixer extends TagWorker {
		@Override
		public void doWork(String line, StringBuilder res) {
			stepTagStack(line);
			stepTagBuffer(res);
			fillHtmlChunk(line, res);
			fillTagBuffer();
		}
	}

	private class TagChecker extends TagWorker {
		@Override
		public void doWork(String line, StringBuilder res) {
			stepTagStack(line);
			if (!checkHtmlTags()) {
				log.error(String.format("Inconsistent HTML tags on line: '%s'.", line));
				log.error(String.format("Tag Stack: '%s'.", tagStack.toString()));
				log.error(String.format("Tag Buffer: '%s'.", tagBuffer.toString()));
			}
		}
	}

	public TagCompensator() {
		this.tagStack = new Stack<>();
		this.tagBuffer = new ArrayList<>();
	}

	public Optional<String> compensateTags(String htmlChunk) {
		if (StringUtils.hasText(htmlChunk)) {
			System.out.println(htmlChunk);

			String compensatedHtml = workOnLines(htmlChunk, new TagFixer());
			clear();

			workOnLines(compensatedHtml, new TagChecker());
			clear();


			System.out.println("---");
			System.out.println(compensatedHtml);
			return Optional.ofNullable(compensatedHtml);
		}
		return Optional.empty();
	}

	protected String workOnLines(String lines, TagWorker worker) {
		StringBuilder res = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new StringReader(lines));
			String line = reader.readLine();
			while (line != null) {
				worker.doWork(line, res);
				line = reader.readLine();
			}
		} catch (IOException ioe) {
			log.error(String.format("Error in Buffered Reader for lines: '%s'.", ioe.getLocalizedMessage()), ioe);
		}
		return res.toString();
	}

	private void clear() {
		tagStack.clear();
		tagBuffer.clear();
	}
}
