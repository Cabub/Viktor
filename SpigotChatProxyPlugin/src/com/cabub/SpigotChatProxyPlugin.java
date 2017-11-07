package com.cabub;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpigotChatProxyPlugin extends JavaPlugin  implements Listener {
	FileConfiguration config = getConfig();
    Logger logger = Bukkit.getLogger();

    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	initializeConfig();
    	
    	// register commands
        this.getCommand("toggle_chat_proxy").setExecutor(new CommandToggleChatProxy());
        this.getCommand("set_proxy_config").setExecutor(new CommandSetProxyConfig());
        
        // register listeners
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	saveConfig();
    }
    
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event)
    {
        String message = event.getMessage();
        if (config.getBoolean("proxy")) {
	        logger.log(Level.INFO, "proxying chat message - \"" + message + "\"");
	        // TODO fire async API call, handle result
	        try {
				apiCall(getProxyURL(), getApiData(event));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        }
    }
    
    private void initializeConfig() {
    	// set configurables
    	config.addDefault("proxy", true);       // whether or not to proxy chat messages
    	config.addDefault("scheme", "http");    // http or https
    	config.addDefault("user", "");          // http basic auth username
    	config.addDefault("password", "");      // http basic auth password
    	config.addDefault("host", "127.0.0.1"); // the hostname (and any subdomains)
    	config.addDefault("port", 9000);        // the server port
    	config.addDefault("path", "/");         // the path in the server we want to request
    	config.addDefault("method", "POST");    // the http method to use when making the call
        config.options().copyDefaults(true);
        saveConfig();
    }
    
    private URL getProxyURL() throws MalformedURLException {
    	return new URL(config.getString("scheme"), 
    			config.getString("host"), 
    			config.getInt("port"), 
    			config.getString("path"));
    }
    
    private void apiCall(URL url, JSONObject json) {
    	// TODO make actual api call
    	logger.log(Level.INFO, "Making request to " + url.toString());
    	//----------------------

    	CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    	try {
    	    HttpPost request = new HttpPost("http://yoururl");
    	    StringEntity params = new StringEntity(json.toString());
    	    request.addHeader("content-type", "application/json");
    	    request.setEntity(params);
    	    CloseableHttpResponse response = httpClient.execute(request);
    	    logger.log(Level.INFO, response.toString());
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    	    try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    @SuppressWarnings("unchecked")
	private JSONObject getApiData(AsyncPlayerChatEvent event) {
    	JSONObject json = new JSONObject();
    	Player player = event.getPlayer();
    	String message = event.getMessage();
    	json.put("username", player.getName());
    	json.put("message", message);
    	return json;
    }

}
