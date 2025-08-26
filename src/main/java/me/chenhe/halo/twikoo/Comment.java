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
                            // 瞬间页格式检测器（可扩展）
                            const momentPageDetectors = [
                                // 检测器1: 检查是否存在moment-comment容器
                                function() {
                                    return document.querySelector('.moment-comment') !== null;
                                },
                                // 检测器2: 检查是否存在moment-body容器
                                function() {
                                    return document.querySelector('.moment-body') !== null;
                                },
                                // 检测器3: 检查是否存在moment-item容器
                                function() {
                                    return document.querySelector('.moment-item') !== null;
                                }
                                // 可以在这里添加更多检测器...
                            ];
                            
                            // 检查是否为瞬间页格式
                            function isMomentPage() {
                                return momentPageDetectors.some(detector => detector());
                            }
                            
                            // 从moment-comment容器获取路径
                            function getMomentCommentPath(container) {
                                const currentPath = window.location.pathname;
                                
                                // 从当前容器向上查找moment-comment
                                let momentComment = container.closest('.moment-comment');
                                
                                // 如果向上找不到，在文档中查找
                                if (!momentComment) {
                                    momentComment = document.querySelector('.moment-comment');
                                }
                                
                                if (!momentComment) {
                                    console.log('[Twikoo] 未找到moment-comment容器');
                                    return null;
                                }                              
                                
                                // 从父级moment-item获取ID
                                const momentItem = momentComment.closest('.moment-item');
                                if (momentItem && momentItem.id) {
                                    const pathSuffix = 'moment-' + momentItem.id.replace('moment-', '');
                                    console.log(`[Twikoo] 使用moment-item ID: ${currentPath}/${pathSuffix}`);
                                    return currentPath + '/' + pathSuffix;
                                }
                              
                                console.log('[Twikoo] moment-comment容器存在但无法提取ID');
                                return null;
                            }
                            
                            // 动态设置path逻辑
                            function getTwikooPath(container) {
                                // 首先检查是否为瞬间页格式
                                if (isMomentPage()) {
                                    console.log('[Twikoo] 检测到瞬间页格式，使用moment-comment策略');
                                    return getMomentCommentPath(container);
                                } else {
                                    console.log('[Twikoo] 非瞬间页格式，使用默认路径');
                                    return undefined;
                                }
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