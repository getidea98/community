package top.getidea.community.dto;

import lombok.Data;

@Data
public class GitHubAccess {

    private String id;
    private String name;
    private String bio;
    private String avatar_url;

    @Override
    public String toString() {
        return "GitHubAccess{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
