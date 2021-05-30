public class Average {

    private double sum;
    private int amount;

    public Average()
    {
        reset();
    }

    public void addNewValue(double newValue)
    {
        sum += newValue;
        amount++;
    }

    public double getAverage()
    {
        if (amount > 0)
            return sum / amount;
        else
            return 0;
    }

    public void reset()
    {
        sum = 0;
        amount = 0;
    }
}
