package com.github.libsgh.mirai.test;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.TempMessageEvent;
import net.mamoe.mirai.utils.BotConfiguration;

public class App {
    public static void main( String[] args ){
		Bot bot = BotFactoryJvm.newBot(0, "", new BotConfiguration() {{
		        //保存设备信息到文件
		        fileBasedDeviceInfo("deviceInfo.json");
		        }
		});
		bot.login();
		Events.registerEvents(bot, new SimpleListenerHost() {
			@NotNull
			@EventHandler()
			public ListeningStatus onReceive(@NotNull TempMessageEvent event) {
			    System.out.println(event.getSender().getId()+event.getMessage().contentToString()+event.getSource().getId()); return 
			    ListeningStatus.LISTENING;
			}
			//处理在处理事件中发生的未捕获异常
            @Override
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                throw new RuntimeException("在事件处理中发生异常", exception);
            }
		});
		bot.join(); // 阻塞当前线程直到 bot 离线
    }
}
