package com.thinkgem.jeesite.common.http;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * 使用一个单独的线程完成连接池中的无效httpClient链接的清理
 * @author ydt
 *
 */
public class IdleHttpClientConnectionEvictor extends Thread {

	private final HttpClientConnectionManager connMgr;

	private volatile boolean shutdown;

	public IdleHttpClientConnectionEvictor(HttpClientConnectionManager connMgr) {
		this.connMgr = connMgr;
		// 启动当前线程
		this.start();
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// 关闭失效的连接
					connMgr.closeExpiredConnections();
				}
			}
		} catch (InterruptedException ex) {
			// 结束
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}