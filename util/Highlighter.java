// Highlighter.java, no comments, no checks.
// $ javac Highlighter.java
// $ jar -cvfe highlighter.jar Highlighter *.class
// $ cat hello.py | java -jar highlighter.jar rouge python
import org.graalvm.polyglot.Context;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Highlighter {
  private abstract class Highlight {
    protected final Context polyglot =
      Context.newBuilder("js", "python", "ruby").allowAllAccess(true).allowIO(true)
        .build();

    protected abstract String language();
    protected abstract String renderHtml(String language, String rawCode);

    protected String execute(String sourceCode) {
      try {
        return polyglot.eval(language(), sourceCode).asString();
      } catch (RuntimeException re) { re.printStackTrace(); }
      return sourceCode;
    }

    protected void importValue(String name, String value) {
      try {
        polyglot.getBindings(language()).putMember(name, value);
      } catch (RuntimeException re) { re.printStackTrace(); }
    }
  }

  private class Hjs extends Highlight {
    @Override
    protected String language() { return "js"; }

    @Override
    public String renderHtml(String language, String rawCode) {
      importValue("source", rawCode);

      String hjs = "";
      try {
        hjs = new Scanner(new File("highlight.min.js")).useDelimiter("\\A").next();
      } catch (FileNotFoundException fnfe) { fnfe.printStackTrace(); }

      final String renderLanguageSnippet =
        hjs + "\n" +
        "hljs.highlight('" + language + "', String(source)).value";
      return execute(renderLanguageSnippet);
    }
  }

  private class Rouge extends Highlight {
    @Override
    protected String language() { return "ruby"; }

    @Override
    public String renderHtml(String language, String rawCode) {
      importValue("$source", rawCode);
      final String renderLanguageSnippet =
        "require 'rouge'" + "\n" +

        "formatter = Rouge::Formatters::HTML.new" + "\n" +
        "lexer = Rouge::Lexer::find('" + language + "')" + "\n" +
        "formatter.format(lexer.lex($source.to_str))";
      return execute(renderLanguageSnippet);
    }
  }

  private class Pygments extends Highlight {
    @Override
    protected String language() { return "python"; }

    @Override
    public String renderHtml(String language, String rawCode) {
      importValue("source", rawCode);
      final String renderLanguageSnippet =
        "import site" + "\n" +
        "from pygments import highlight" + "\n" +
        "from pygments.lexers import get_lexer_by_name" + "\n" +
        "from pygments.formatters import HtmlFormatter" + "\n" +

        "formatter = HtmlFormatter(nowrap=True)" + "\n" +
        "lexer = get_lexer_by_name('" + language + "')" + "\n" +
        "highlight(source, lexer, formatter)";
      return execute(renderLanguageSnippet);
    }
  }

  public String highlight(String library, String language, String code) {
    switch (library) {
      default:
      case "hjs": return new Hjs().renderHtml(language, code);
      case "rouge": return new Rouge().renderHtml(language, code);
      case "pygments": return new Pygments().renderHtml(language, code);
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in).useDelimiter("\\A");
    if (scanner.hasNext()) {
      String code = scanner.next();
      if (!code.isEmpty()) {
        System.out.println(new Highlighter().highlight(args[0], args[1], code));
      }
    }
  }
}
