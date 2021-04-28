///*
//
//
// * JBoss, Home of Professional Open Source
// * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
// * contributors by the @authors tag. See the copyright.txt in the
// * distribution for a full listing of individual contributors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package bbr.b2b.logistic.timers.managers.utils;
//
//import java.util.Date;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//
//import org.apache.log4j.Logger;
//import org.jboss.as.server.ServerEnvironment;
//import org.jboss.msc.service.Service;
//import org.jboss.msc.service.ServiceName;
//import org.jboss.msc.service.StartContext;
//import org.jboss.msc.service.StartException;
//import org.jboss.msc.service.StopContext;
//import org.jboss.msc.value.Value;
//
//import bbr.b2b.logistic.timers.managers.classes.PendingMessageService;
//import bbr.b2b.logistic.timers.managers.classes.SoaNotificationService;
//import bbr.b2b.logistic.timers.managers.interfaces.Scheduler;
//
//public class HATimerService implements Service<Environment> {
//
//	// NOMBRE DEL SERVICIO
//	public static final ServiceName DEFAULT_SERVICE_NAME = ServiceName.JBOSS.append("service", "ha", "singleton", "default");
//
//	// NODOS
//	public static final String NODE_1 = "logistic-module-1";
//
//	public static final String NODE_2 = "logistic-module-2";
//
//	private static final Logger logger = Logger.getLogger(HATimerService.class);
//
//	private final Value<ServerEnvironment> env;
//
//	private final AtomicBoolean started = new AtomicBoolean(false);
//
//	public HATimerService(Value<ServerEnvironment> env) {
//		this.env = env;
//	}
//
//	@Override
//	public Environment getValue() {
//		if (!this.started.get()) {
//			throw new IllegalStateException();
//		}
//		return new Environment(this.env.getValue().getNodeName());
//	}
//
//	@Override
//	public void start(StartContext context) throws StartException {
//		if (!started.compareAndSet(false, true)) {
//			throw new StartException("El servicio ya fue iniciado");
//		}
//		logger.info("Iniciando servicio de timers");
//
//		try {
//			InitialContext ic = new InitialContext();
//
//			// INICIA LOS TIMERS
//			logger.info("Iniciando CrawlerFileDownloader");
//			logger.info("Node name: " + this.env.getValue().getNodeName());
//
//			((Scheduler) ic.lookup(SoaNotificationService.JNDI_TIMER)).initialize("SoaNotificationService: " + this.env.getValue().getNodeName() + " " + new Date(), 40000L, 60000L * 5);
//
//			((Scheduler) ic.lookup(PendingMessageService.JNDI_TIMER)).initialize("PendingMessageService: " + this.env.getValue().getNodeName() + " " + new Date(), 30000L, 60000L * 5);
//
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void stop(StopContext context) {
//		if (!started.compareAndSet(true, false)) {
//			logger.warn("El servicio '" + this.getClass().getName() + "' no esta activo!");
//		} else {
//			logger.info("Deteniendo el servicio de timers");
//			try {
//				InitialContext ic = new InitialContext();
//
//				//				 DETIENE LOS TIMERS
//				((Scheduler) ic.lookup(SoaNotificationService.JNDI_TIMER)).stop();
//				((Scheduler) ic.lookup(PendingMessageService.JNDI_TIMER)).stop();
//			} catch (NamingException e) {
//				logger.error("no es posible detener los timers", e);
//			}
//		}
//	}
//}
