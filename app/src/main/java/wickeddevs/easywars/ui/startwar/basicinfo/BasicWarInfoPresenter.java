package wickeddevs.easywars.ui.startwar.basicinfo;

/**
 * Created by 375csptssce on 8/18/16.
 */
public class BasicWarInfoPresenter implements BasicWarInfoContract.ViewListener {

    private BasicWarInfoContract.View view;
    private int warSize = 10;
    private int hours = 23;
    private int minutes = 59;

    @Override
    public void increaseWarSize() {
        if (warSize != 50) {
            if (warSize == 30 || warSize == 40) {
                warSize += 10;
            } else {
                warSize += 5;
            }
            view.setWarSizeText(String.valueOf(warSize));
        }
    }

    @Override
    public void decreaseWarSize() {
        if (warSize != 10) {
            if (warSize == 40 || warSize == 50) {
                warSize -= 10;
            } else {
                warSize -= 5;
            }
            view.setWarSizeText(String.valueOf(warSize));
        }
    }


    @Override
    public long getStartTimeMilis(long currentTimeMilis, boolean tilWarEnd) {
        long nowTime = System.currentTimeMillis();
        long miliSecondsElapsed = (((23 - hours) * 60) + (60 - minutes)) * 60000;
        long warStart = nowTime - miliSecondsElapsed;
        if (tilWarEnd) {
            warStart -= 86400000; // Miliseconds in a day
        }
        return warStart;
    }

    @Override
    public void registerView(BasicWarInfoContract.View activity) {
        view = activity;
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
