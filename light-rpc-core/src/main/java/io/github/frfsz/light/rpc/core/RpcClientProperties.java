/**
 * 文件名：RpcClient.java 版权：Copyright © Suzhou Keda Technology Co.Ltd. All Rights Res erved.
 */
package io.github.frfsz.light.rpc.core;
/**
 * @description
 * @author fengsheng
 * @since 2020年4月24日
 * @date 2020年4月24日
 */
public class RpcClientProperties {
    private String name[] = new String[] {};;
    private String host = "localhost";
    private int port = 1100;

    /**
     * @return the name
     */
    public String[] getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String[] name) {
        this.name = name;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(final String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(final int port) {
        this.port = port;
    }

}
