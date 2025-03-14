package controller.action.ui.penalty;

import common.Log;
import data.AdvancedData;
import data.GameControlData;
import data.PlayerInfo;
import data.Rules;

/**
 * This action means that the foul penalty (pushing + free kick) has been selected.
 *
 * @author Arne Hasselbring
 */
public class Foul extends Pushing
{
    /**
     * Performs this action`s penalty on a selected player.
     *
     * @param data      The current data to work on.
     * @param player    The player to penalise.
     * @param side      The side the player is playing on (0:left, 1:right).
     * @param number    The player`s number, beginning with 0!
     */
    @Override
    public void performOn(AdvancedData data, PlayerInfo player, int side, int number)
    {
        super.performOn(data, player, side, number);

        data.whenCurrentSetPlayBegan = data.getTime();
        data.setPlay = GameControlData.SET_PLAY_PUSHING_FREE_KICK;
        data.kickingTeam = data.team[1 - side].teamNumber;

        Log.state(data, "Pushing Free Kick for "+Rules.league.teamColorName[data.team[1 - side].fieldPlayerColor]);
    }

    /**
     * Checks if this action is legal with the given data (model).
     * Illegal actions are not performed by the EventHandler.
     *
     * @param data      The current data to check with.
     */
    @Override
    public boolean isLegal(AdvancedData data)
    {
        return super.isLegal(data) && ((data.gameState == GameControlData.STATE_PLAYING)
                    && (data.gamePhase != GameControlData.GAME_PHASE_PENALTYSHOOT)
                    && (data.setPlay == GameControlData.SET_PLAY_NONE)
                    && (data.competitionType != GameControlData.COMPETITION_TYPE_DYNAMIC_BALL_HANDLING)
                    || data.testmode);
    }
}
