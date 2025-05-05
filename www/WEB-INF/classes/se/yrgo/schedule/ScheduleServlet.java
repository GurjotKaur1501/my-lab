package se.yrgo.schedule;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ScheduleServlet extends HttpServlet {
  private Assignments assignments;

  @Override
  public void init() {
      DBHelper db = new DBHelper(getServletContext());
      this.assignments = new DatabaseAssignments(db);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

    try {
      ParamParser parser = new ParamParser(request);
      response.setContentType(parser.contentType());

      List<Assignment> result = getAssignments(parser);

      Formatter formatter = FormatterFactory.getFormatter(parser.format());
      String formattedResult = formatter.format(result);
      out.println(formattedResult);
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType("text/html");
      out.println("<html><head><title>Format error</title></head>");
      out.println("<body>Format missing or not supported");
      out.println(" - We support xml and json</body>");
      out.println("</html>");
    } catch (AccessException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.setContentType("text/html");
      out.println("<html><head><title>Not Found</title></head>");
      out.println("<body>" + e.getMessage() + "</body>");
      out.println("</html>");
    } finally {
      out.close();
    }
  }

  private List<Assignment> getAssignments(ParamParser parser) throws AccessException {
    if (parser.hasSubstituteId() && parser.hasDay()) {
      return assignments.forTeacherAt(parser.substituteId(), parser.day());
    } else if (parser.hasSubstituteId()) {
      return assignments.forTeacher(parser.substituteId());
    } else if (parser.hasDay()) {
      return assignments.at(parser.day());
    } else {
      return assignments.all();
    }
  }
}