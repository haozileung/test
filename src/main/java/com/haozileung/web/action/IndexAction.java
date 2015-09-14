package com.haozileung.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexAction {
    public String get(HttpServletRequest request, HttpServletResponse response) {
//        List<Post> page = CacheHelper.get("Post", "index", new ICacheInvoker<List<Post>>() {
//            @Override
//            public List<Post> callback() {
//                return JdbcDaoUtil.getInstance().queryList(Criteria.create(Post.class).desc("createTime").limit(10));
//            }
//        });
//        //request.setAttribute("page", page);
        return "/index.html";
    }
}
