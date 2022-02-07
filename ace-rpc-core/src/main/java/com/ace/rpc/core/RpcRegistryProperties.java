package com.ace.rpc.core;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fengsheng
 * @date 2020年6月21日
 */
public class RpcRegistryProperties{

    private int serverPort = 1100;

    private List<RpcClientProperties> clients = new LinkedList<>();

    /**
     * @return the serverPort
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * @param serverPort
     *            the serverPort to set
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @return the clients
     */
    public List<RpcClientProperties> getClients() {
        return clients;
    }

    /**
     * @param clients
     *            the clients to set
     */
    public void setClients(List<RpcClientProperties> clients) {
        this.clients = clients;
    }

}