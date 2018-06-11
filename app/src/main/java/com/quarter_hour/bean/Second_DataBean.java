package com.quarter_hour.bean;

public class Second_DataBean {
    /**
     * commentNum : null
     * content : 朋友不是玻璃做的，有许多人总是把朋友当做玻璃，小心翼翼的怕碰坏了。有时候，明明对朋友很不满，却不敢表达出来，害怕一旦表达不满，就会发生冲突；一旦发生冲突，就会伤害感情；一旦伤害感情，就失去这个朋友。真正的朋友不是玻璃做的，如果朋友真的像玻璃一样不许你碰，这样的朋友破了就破了吧。
     * createTime : 2018-06-08T18:54:43
     * imgUrls : null
     * jid : 2688
     * praiseNum : null
     * shareNum : null
     * uid : 13850
     * user : {"age":null,"fans":"null","follow":false,"icon":null,"nickname":null,"praiseNum":"null"}
     */

    private Object commentNum;
    private String content;
    private String createTime;
    private Object imgUrls;
    private int jid;
    private Object praiseNum;
    private Object shareNum;
    private int uid;
    private UserBean user;

    public Object getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Object commentNum) {
        this.commentNum = commentNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(Object imgUrls) {
        this.imgUrls = imgUrls;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public Object getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Object praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Object getShareNum() {
        return shareNum;
    }

    public void setShareNum(Object shareNum) {
        this.shareNum = shareNum;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * age : null
         * fans : null
         * follow : false
         * icon : null
         * nickname : null
         * praiseNum : null
         */

        private Object age;
        private String fans;
        private boolean follow;
        private Object icon;
        private Object nickname;
        private String praiseNum;

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public boolean isFollow() {
            return follow;
        }

        public void setFollow(boolean follow) {
            this.follow = follow;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public String getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(String praiseNum) {
            this.praiseNum = praiseNum;
        }
    }
}
