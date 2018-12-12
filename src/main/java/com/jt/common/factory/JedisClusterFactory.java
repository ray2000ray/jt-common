package com.jt.common.factory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster>{

	private Resource propertySource; //动态引入配置文件
	
	//@Autowired
	//统一使用set注入
	private JedisPoolConfig poolConfig;
	
	private String redisNodePrefix;
	
	public Resource getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(Resource propertySource) {
		this.propertySource = propertySource;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public String getRedisNodePrefix() {
		return redisNodePrefix;
	}

	public void setRedisNodePrefix(String redisNodePrefix) {
		this.redisNodePrefix = redisNodePrefix;
	}
	
	//获取节点方法
	public Set<HostAndPort> getNodes(){
		Set<HostAndPort> nodes = new HashSet<>();
				Properties properties = new Properties();
		try {
			properties.load(propertySource.getInputStream());
			for( Object key : properties.keySet()) {
				String strKey = (String) key;
				if(strKey.startsWith(redisNodePrefix)) {
					//获取redis节点数据ip:端口
					String value = properties.getProperty(strKey);
					String[] args = value.split(":");
					HostAndPort hostAndPort = new HostAndPort(args[0], Integer.parseInt(args[1]));
					nodes.add(hostAndPort);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodes;
		
	}
	

	@Override
	public JedisCluster getObject() throws Exception {
		// TODO Auto-generated method stub
		Set<HostAndPort> nodes = getNodes();
		
;		return new JedisCluster(nodes, poolConfig);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return JedisCluster.class;
		//return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
