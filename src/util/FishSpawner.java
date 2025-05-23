package util;

import entity.Fish;
import entity.SmallFish;

import java.util.List;

public class FishSpawner
{
    private int counter = 0;
    private final List<Fish> fishes;
    private final int width, height;

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
}
