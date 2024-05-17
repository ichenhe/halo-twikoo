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

        final var tmpl = """
            <div id="twikoo-comment"></div>
            <script src="%s"></script>
            <script>
                twikoo.init({
                    envId: '%s',
                    el: '#twikoo-comment',
                })
            </script>
            """;

        structureHandler.replaceWith(tmpl.formatted(jsUrl, envId), false);
    }
}