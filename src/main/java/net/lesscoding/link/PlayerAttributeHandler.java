package net.lesscoding.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lesscoding.model.dto.CurrentBattleProcess;
import net.lesscoding.utils.BattleUtil;

@NoArgsConstructor
@Getter
@Setter
public class PlayerAttributeHandler extends BattleRequestHandler {
    @Override
    public void handleRequest(BattleRequest request) {

        CurrentBattleProcess currentBattleProcess = request.getCurrentBattleProcess();

        Boolean fleeFlag = BattleUtil.getWeightResult(currentBattleProcess.getFlee());
        Boolean criticalFlag = BattleUtil.getWeightResult(currentBattleProcess.getCriticalChance());

        currentBattleProcess.setFleeFlag(fleeFlag);
        currentBattleProcess.setCriticalFlag(criticalFlag);
    }
}
