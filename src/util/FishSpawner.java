package util;

import entity.*;

import java.util.List;

public class FishSpawner
{
//    private int counter = 0;
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

    private int smallFishCounter = 0;
    private int mediumFishCounter = 0;
    private int largeFishCounter = 0;

    public void update(int playerScore)
    {
        smallFishCounter++;
        mediumFishCounter++;
        largeFishCounter++;

        if (bossWarning)//开摆
        {
            warningCounter++;
            if (warningCounter >= 60)
            {
                fishes.add(new Boss(width, height));
                bossWarning = false;
                warningCounter = 0;
                nextBossScoreThreshold += 10;
            }
        }
        else if (playerScore >= nextBossScoreThreshold)
        {
            bossWarning = true;
            warningCounter = 0;
        }
        else
        {
            if (smallFishCounter >= 20)
            {
                smallFishCounter = 0;
                fishes.add(new SmallFish(width, height));
            }
            if (mediumFishCounter >= 150)
            {
                mediumFishCounter = 0;
                fishes.add(new MediumFish(width, height));
            }
            if (largeFishCounter >= 300)
            {
                largeFishCounter = 0;
                fishes.add(new LargeFish(width, height));
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
//        counter = 0;
        nextBossScoreThreshold = 20;  // 重置到初始值
    }

}
