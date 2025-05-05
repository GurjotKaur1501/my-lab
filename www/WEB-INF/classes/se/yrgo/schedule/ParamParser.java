package se.yrgo.schedule;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ParamParser {
  private static final Charset UTF_8 = StandardCharsets.UTF_8;
  private final String format;
  private final String substituteId;
  private final String day;
  private String contentType;

  public ParamParser(HttpServletRequest request) {
    this.format = request.getParameter("format");
    this.substituteId = request.getParameter("substitute_id");
    this.day = request.getParameter("day");
    parseContentType();
  }

  private void parseContentType() {
    if (format != null && format.equalsIgnoreCase("json")) {
      contentType = "application/json;charset=" + UTF_8.name();
    } else if (format != null && format.equalsIgnoreCase("xml")) {
      contentType = "application/xml;charset=" + UTF_8.name();
    } else {
      contentType = "text/html;charset=" + UTF_8.name();
    }
  }

  public String format() {
    return format;
  }

  public String substituteId() {
    return substituteId;
  }

  public String day() {
    return day;
  }

  public String contentType() {
    return contentType;
  }

  public boolean hasSubstituteId() {
    return substituteId != null && !substituteId.isEmpty();
  }

  public boolean hasDay() {
    return day != null && !day.isEmpty();
  }
}