package com.haozileung.web.action.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = { "/syncServlet" }, asyncSupported = true, loadOnStartup = 1)
public class TestAction extends HttpServlet {
	private static final long serialVersionUID = -126107068129496624L;
	private final BlockingQueue<Runnable> quere = new ArrayBlockingQueue<Runnable>(10);
	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.HOURS, quere);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		final AsyncContext sc = request.startAsync(request, response);
		final StringBuffer content = new StringBuffer("<html><body>");
		content.append("<div id='a1' style='width:100px;height:100px;border:1px solid black'></div>");
		content.append("<div id='a2' style='width:100px;height:100px;border:1px solid black'></div>");
		content.append("<div id='a3' style='width:100px;height:100px;border:1px solid black'></div>");
		content.append("<div id='a4' style='width:100px;height:100px;border:1px solid black'></div>");
		content.append("<script type='text/javascript' src='test.js'></script>");
		content.append("</body></html>");
		final PrintWriter pw = response.getWriter();
		pw.println(content.toString());
		pw.flush();
		executor.execute(new Job(sc, 1));
		executor.execute(new Job(sc, 2));
		executor.execute(new Job(sc, 3));
		executor.execute(new Job(sc, 4));
	}

}

class Job implements Runnable {
	private static int count;
	private AsyncContext syncContext;
	private int no;

	public Job(AsyncContext syncContext, int no) {
		this.syncContext = syncContext;
		this.no = no;
		count++;
	}

	public void run() {
		HttpServletResponse resp = (HttpServletResponse) syncContext.getResponse();
		try {
			Thread.sleep(no * 1000);
			PrintWriter out = resp.getWriter();
			out.println(
					"<script type='text/javascript'>document.all.a" + no + ".innerHTML='<h1>" + no + "</h1>'</script>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		count--;
		if (count == 0) {
			syncContext.complete();
		}
	}
}