package com.candao.member.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.candao.member.plugin.common.PermissionCollection;


public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		PermissionCollection.getInstance().removeSessionUserCache(event.getSession().getId());

	}

}
