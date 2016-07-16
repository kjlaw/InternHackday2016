package com.fantasticthree.funapp.data;

public class ImageResponse {
    private final String mUserId;
    private final String mCompany;
    private final String mFullName;
    private final String mEmail;

    private ImageResponse(Builder builder) {
        mUserId = builder.userid;
        mCompany = builder.company;
        mFullName = builder.fullname;
        mEmail = builder.email;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getCompany() {
        return mCompany;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getEmail() {
        return mEmail;
    }

    public static class Builder {
        private String userid;
        private String company;
        private String fullname;
        private String email;

        public Builder withUserid(String userid) {
            this.userid = userid;
            return this;
        }

        public Builder withCompany(String company) {
            this.company = company;
            return this;
        }

        public Builder withFullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ImageResponse build() {
            return new ImageResponse(this);
        }
    }
}