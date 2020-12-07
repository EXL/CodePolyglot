package ru.exlmoto.code.highlight.filter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * Useful information:
 *  https://www.baeldung.com/jsoup-line-breaks
 *  https://github.com/wcoder/highlightjs-line-numbers.js/blob/40eff67eb7349d8c008e0b49f4f7e74688522c36/src/highlightjs-line-numbers.js#L278-L325
 */
@Component
public class TagCompensator {
	private final Logger log = LoggerFactory.getLogger(TagCompensator.class);

	public Optional<String> compensateTags(String htmlChunk) {
		if (StringUtils.hasText(htmlChunk)) {
			try {
				Document document = Jsoup.parse(htmlChunk);
				document.outputSettings().prettyPrint(false);

				Element element = document.getElementsByTag("body").first();
				compensateTagsOnHtmlNodes(element.childNodes());
				return Optional.ofNullable(element.outerHtml());
			} catch (RuntimeException re) {
				log.error(String.format("HTML Tag Compensator error: '%s'.", re.getLocalizedMessage()), re);
			}
		}
		return Optional.empty();
	}

	protected void compensateTagsOnHtmlNodes(List<Node> nodes) {
		for (Node child : nodes) {
			if (getLinesCount(child.outerHtml()) > 0) {
				if (child.childNodeSize() > 0) {
					compensateTagsOnHtmlNodes(child.childNodes());
				} else {
					compensateTagsOnHtmlLastNode(child.parentNode());
				}
			}
		}
	}

	protected void compensateTagsOnHtmlLastNode(Node node) {
		final String tagName = node.nodeName();
		if (tagName != null && tagName.startsWith("span")) {
			final String className = node.attr("class");
			final StringBuilder stringBuilder = new StringBuilder();

			List<String> lines = new ArrayList<>();
			node.childNodes().forEach((child) -> lines.addAll(Arrays.asList(child.outerHtml().split("\n"))));

			for (String line : lines) {
				if (StringUtils.hasText(className)) {
					stringBuilder.append(String.format("<span class\"%s\">%s</span>\n", className, line));
				} else {
					stringBuilder.append(String.format("<span>%s</span>\n", line));
				}
			}
			node.replaceWith(new DataNode(stringBuilder.toString()));
		}
	}

	protected int getLinesCount(String entity) {
		return (entity.contains("\n")) ? entity.trim().split("\n").length - 1 : 0;
	}
}
