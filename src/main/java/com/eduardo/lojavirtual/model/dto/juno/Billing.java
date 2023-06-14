package com.eduardo.lojavirtual.model.dto.juno;

public class Billing {

    private String nome;
    private String document;
    private String email;
    private String phone;
    private boolean notify = true;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

}
