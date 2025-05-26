package util;

import entity.Boss;
import entity.Fish;
import entity.SmallFish;

import java.util.List;

public class FishSpawner
{
    private int counter = 0;
    private final List<Fish> fishes;
    private final int width, height;

    private boolean bossWarning = false;      // 正在提示生成Boss
    private int warningCounter = 0;            // 提示计时
    private int nextBossScoreThreshold = 20;  // 下一个生成Boss的积分阈值

    public FishSpawner(List<Fish> fishes, int width, int height)
    {
        this.fishes = fishes;
        this.width = width;
        this.height = height;
    }

    public void update()
    {
        counter++;
        if (counter >= 20)
        {
            counter = 0;
            fishes.add(new SmallFish(width, height));
        }
    }
    public void update(int playerScore)
    {
        counter++;

        if (playerScore >= nextBossScoreThreshold)
        {
            fishes.add(new Boss(Default.getWindowWidth(),Default.getWindowHeight()));
            nextBossScoreThreshold += 10;
            // Optional: 显示提示，比如设置 showBossWarning = true;
        }


        if (bossWarning)
        {
            warningCounter++;
            // 提示倒计时，比如60帧（1秒）
            if (warningCounter >= 60)
            {
                fishes.add(new Boss(width, height));
                bossWarning = false;
                warningCounter = 0;
                nextBossScoreThreshold += 10;  // 阈值递增10分
            }
        }
        else
        {
            // 判断是否达到生成Boss的条件
            if (playerScore >= nextBossScoreThreshold)
            {
                bossWarning = true;
                warningCounter = 0;
            }
            else
            {
                // 继续生成小鱼
                if (counter >= 20)
                {
                    counter = 0;
                    fishes.add(new SmallFish(width, height));
                }
            }
        }
    }
    public boolean isBossWarning()
    {
        return bossWarning;
    }

    public int getWarningCounter()
    {
        return warningCounter;
    }

    public void reset()
    {
        bossWarning = false;
        warningCounter = 0;
        counter = 0;
        nextBossScoreThreshold = 20;  // 重置到初始值
    }

}
