package se.yrgo.schedule;

public class FormatterFactory {
  private static final Formatter JSON_FORMATTER = new JsonFormatter();
  private static final Formatter XML_FORMATTER = new XmlFormatter();

  public static Formatter getFormatter(String contentType) {
    if (contentType.equalsIgnoreCase("xml")) {
      return XML_FORMATTER;
    } else if (contentType.equalsIgnoreCase("json")) {
      return JSON_FORMATTER;
    } else {
      throw new IllegalArgumentException("Format not supported");
    }
  }
}