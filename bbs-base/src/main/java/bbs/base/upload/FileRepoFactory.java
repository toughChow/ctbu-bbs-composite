package bbs.base.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class FileRepoFactory {
    private Map<String, FileRepo> fileRepos = new HashMap<>();

    @Value("${site.store.repo}")
    private String repo = "relative";

    public void addRepo(String key, FileRepo value) {
        fileRepos.put(key, value);
    }

    public FileRepo select() {
        return fileRepos.get(repo);
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

}
