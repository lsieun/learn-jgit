package lsieun.git.porcelain.everyday;

import org.eclipse.jgit.api.Git;

import java.io.File;

public class MyApp {
    public static void main(String[] args) {
        // 只需要这一行，后续所有的 JGit 操作都会自动带上代理和凭据
        JGitConfigManager.getInstance().init("https", "github.com");

        try {
            // 示例：直接 Clone，无需再写 .setCredentialsProvider
            Git git = Git.cloneRepository()
                .setURI("https://github.com")
                .setDirectory(new File("C:/my-repo"))
                .call();

            // 示例：直接 Push
            git.push().call();
            
            System.out.println("操作全部成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
