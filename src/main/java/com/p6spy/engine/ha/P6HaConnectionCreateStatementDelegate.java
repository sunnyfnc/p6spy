/*
 * #%L
 * P6Spy
 * %%
 * Copyright (C) 2002 - 2014 P6Spy
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.p6spy.engine.ha;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.proxy.Delegate;
import com.p6spy.engine.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.sql.Statement;

/**
 * User: kataev
 * Date: 10.06.14
 */
public class P6HaConnectionCreateStatementDelegate implements Delegate {

    private final ConnectionInformation connectionInformation;

    public P6HaConnectionCreateStatementDelegate(ConnectionInformation connectionInformation) {
        this.connectionInformation = connectionInformation;
    }

    @Override
    public Object invoke(Object proxy, Object underlying, Method method, Object[] args) throws Throwable {
        Statement statement = (Statement) method.invoke(underlying, args);
        P6HaStatementInvocationHandler invocationHandler = new P6HaStatementInvocationHandler(statement, connectionInformation);
        return ProxyFactory.createProxy(statement, invocationHandler);
    }

    protected ConnectionInformation getConnectionInformation() {
        return connectionInformation;
    }
}
