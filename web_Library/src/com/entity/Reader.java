package com.entity;

/**
 * 将从数据库获取到的数据封装成对象，也算M层
 */
public class Reader {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String tel;
    private String cardId;
    private String gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 读者对象完整的有参构造
     *
     * @param id
     * @param username
     * @param password
     * @param name
     * @param tel
     * @param cardId
     * @param gender
     */
    public Reader(Integer id, String username, String password, String name, String tel, String cardId, String gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.cardId = cardId;
        this.gender = gender;
    }

    /**
     * 给借阅时生成读者对象用，因为用不到实际读者的所有属性，只需要下面几个即可
     *
     * @param name
     * @param tel
     * @param cardId
     */
    public Reader(String name, String tel, String cardId) {
        this.name = name;
        this.tel = tel;
        this.cardId = cardId;
    }
}
