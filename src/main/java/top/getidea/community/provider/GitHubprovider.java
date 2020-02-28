package top.getidea.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import top.getidea.community.dto.GitHubAccess;
import top.getidea.community.dto.GitHubAccessTokenDTO;

import java.io.IOException;

@Component
public class GitHubprovider {

    public String getGitHubAccessToken(GitHubAccessTokenDTO gitHubAccessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(gitHubAccessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            return result.split("&")[0].split("=")[1];
        } catch (IOException e) {

        }
        return null;
    }

    public GitHubAccess getGitHubAccessInfo(String Access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+Access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubAccess gitHubAccess = JSON.parseObject(string, GitHubAccess.class);
            return gitHubAccess;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
