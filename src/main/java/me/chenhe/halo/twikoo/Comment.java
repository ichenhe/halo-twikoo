package me.chenhe.halo.twikoo;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import run.halo.app.plugin.SettingFetcher;
import run.halo.app.theme.dialect.CommentWidget;

@Component
public class Comment implements CommentWidget {

    private final SettingFetcher settingFetcher;

    public Comment(SettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    @Override
    public void render(ITemplateContext context, IProcessableElementTag tag,
            IElementTagStructureHandler structureHandler) {

        var settings = settingFetcher.get("basic");
        final var jsUrl = settings.get("jsUrl").asText();
        final var envId = settings.get("envId").asText();

        // 生成唯一的容器ID
        final var uniqueId = "twikoo-comment-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
        
        final var tmpl = """
                <div id="%s"></div>
                <script>
                    (function() {
                        // 确保twikoo脚本只加载一次
                        if (!window.twikooLoaded) {
                            const script = document.createElement('script');
                            script.src = '%s';
                            script.onload = function() {
                                window.twikooLoaded = true;
                                initTwikoo();
                            };
                            document.head.appendChild(script);
                        } else {
                            // 如果已经加载，直接初始化
                            initTwikoo();
                        }
                        
                        function initTwikoo() {
                            // 动态设置path逻辑
                            function getTwikooPath(container) {
                                const momentBody = container.closest('.moment-item') || 
                                                 container.querySelector('.moment-body') ||
                                                 document.querySelector('.moment-body');
                                if (!momentBody) {
                                    return undefined; // 没有moment-body容器，不设置path
                                }
                                
                                // 查找moment-item容器获取ID
                                const momentItem = momentBody.closest('.moment-item');
                                if (momentItem && momentItem.id) {
                                    const currentPath = window.location.pathname;
                                    return currentPath + '/moment-' + momentItem.id.replace('moment-', '');
                                }
                                
                                return undefined;
                            }
                            
                            const container = document.getElementById('%s');
                            if (!container) return;
                            
                            const initConfig = {
                                envId: '%s',
                                el: '#%s',
                            };
                            
                            const dynamicPath = getTwikooPath(container);
                            if (dynamicPath) {
                                initConfig.path = dynamicPath;
                            }
                            
                            // 销毁可能存在的旧实例
                            if (window.twikoo && typeof window.twikoo.destroy === 'function') {
                                try {
                                    window.twikoo.destroy('#%s');
                                } catch(e) {
                                    // 忽略销毁错误
                                }
                            }
                            
                            // 初始化新实例
                            window.twikoo.init(initConfig);
                        }
                    })();
                </script>
                """;

        structureHandler.replaceWith(tmpl.formatted(uniqueId, jsUrl, uniqueId, envId, uniqueId, uniqueId), false);
    }
}