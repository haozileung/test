package com.haozileung.web.action.blog;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.ext.servlet.ServletGroupTemplate;

@WebServlet(value = { "/syncServlet" }, asyncSupported = true, loadOnStartup = 1)
public class TestAction extends HttpServlet {
	private static final long serialVersionUID = -126107068129496624L;
	private final BlockingQueue<Runnable> quere = new ArrayBlockingQueue<Runnable>(
			10);
	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
			10, TimeUnit.HOURS, quere);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("time", System.currentTimeMillis());
		ServletGroupTemplate.instance().render("syncTest/index.html", request,
				response);
		final AsyncContext sc = request.startAsync(request, response);
		CountDownLatch c = new CountDownLatch(4);
		executor.execute(new Job(c, sc, 1));
		executor.execute(new Job(c, sc, 2));
		executor.execute(new Job(c, sc, 3));
		executor.execute(new Job(c, sc, 4));
		executor.execute(new Stop(sc, c));
	}

}

class Job implements Runnable {
	private AsyncContext syncContext;
	private int no;
	CountDownLatch c;

	public Job(CountDownLatch c, AsyncContext syncContext, int no) {
		this.syncContext = syncContext;
		this.no = no;
		this.c = c;
	}

	public void run() {
		HttpServletRequest request = (HttpServletRequest) syncContext
				.getRequest();
		HttpServletResponse response = (HttpServletResponse) syncContext
				.getResponse();
		try {
			synchronized (syncContext) {
				request.setAttribute("no", no);
				ServletGroupTemplate.instance().render("syncTest/part.html",
						request, response);
				c.countDown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Stop implements Runnable {
	CountDownLatch c;
	AsyncContext syncContext;

	public Stop(AsyncContext syncContext, CountDownLatch c) {
		this.c = c;
		this.syncContext = syncContext;
	}

	public void run() {
		try {
			c.await();
			syncContext.complete();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}