<div align="center">
    <img alt="logo" width="102px" src="https://github.com/ichenhe/halo-twikoo/assets/10266066/10d1c1af-b6d3-45c3-ae54-cb74e3dbbe4d">
    <h1>Halo - Twikoo</h1>
    <p>将 <a href="https://twikoo.js.org/">Twikoo</a> 评论系统集成到 <a href="https://www.halo.run/">Halo</a>。</p>
    <p align="center">
        <a href="https://www.halo.run/store/apps/app-FgtLY"><img alt="Halo App Store" src="https://img.shields.io/badge/Halo-%E5%BA%94%E7%94%A8%E5%B8%82%E5%9C%BA-%230A81F5?style=flat-square&logo=appstore&logoColor=%23fff" /></a>
        <a href="//github.com/ichenhe/halo-twikoo/releases"><img alt="GitHub Release" src="https://img.shields.io/github/v/release/ichenhe/halo-twikoo?style=flat-square&logo=github" /></a>
        <a href="//github.com/ichenhe/halo-twikoo/actions/workflows/ci.yaml"><img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/ichenhe/halo-twikoo/ci.yaml?style=flat-square&label=build" /></a>
        <a href="./LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/ichenhe/halo-twikoo?style=flat-square" /></a>
    </p>
</div>

本插件理论上支持所有使用 Halo 默认评论系统的主题。

## 📖 使用说明

### 激活插件

#### Halo v2.17+

安装并启用插件后，进入 Halo 后台「插件-扩展配置-评论组件」，选中本插件。

<img width="1591" alt="image" src="https://github.com/user-attachments/assets/de178382-50f6-4e4e-afd0-d1894dc0d48d">


#### < Halo v2.17

请禁用其他评论插件（例如「评论组件」）确保本插件生效


### 前端脚本地址

输入 Twikoo js 脚本的链接，通常使用默认格式即可，但请注意修改版本号，与后端匹配。

```
https://cdn.staticfile.org/twikoo/1.6.34/twikoo.all.min.js
                               | 修改这里 |
```

### envId

这里要填写的值根据后端 Twikoo 部署方式的不同而不同。

- 腾讯云云函数部署：腾讯云的 envId
- Vercel 部署：`https://xxx.vercel.app`
- 自托管部署：Twikoo 服务的访问地址 (`https://xxxx`)

其他部署方式请参考 Twikoo 的文档。
