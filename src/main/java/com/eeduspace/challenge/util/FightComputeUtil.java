package com.eeduspace.challenge.util;

import java.math.BigDecimal;

public class FightComputeUtil {
	/**
	 * 挑战战斗力值换算
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月13日 下午2:36:36
	 * @param combatValue
	 * @return
	 */
	public static Long challengeCombatValueCompute(Long combatValue) {
		BigDecimal baseBigDecimal = new BigDecimal(combatValue);
		if (0l <= combatValue && combatValue < 250) {
			return baseBigDecimal.multiply(new BigDecimal(0.01))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (250l <= combatValue && combatValue < 500) {
			return baseBigDecimal.multiply(new BigDecimal(0.0095))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (500l <= combatValue && combatValue < 750) {
			return baseBigDecimal.multiply(new BigDecimal(0.009))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (750l <= combatValue && combatValue < 1000) {
			return baseBigDecimal.multiply(new BigDecimal(0.0085))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (1000l <= combatValue && combatValue < 1250) {
			return baseBigDecimal.multiply(new BigDecimal(0.008))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();

		}
		if (1250l <= combatValue && combatValue < 1500) {
			return baseBigDecimal.multiply(new BigDecimal(0.0075))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (1500l <= combatValue && combatValue < 1750) {
			return baseBigDecimal.multiply(new BigDecimal(0.007))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (1750l <= combatValue && combatValue < 2000) {

			return baseBigDecimal.multiply(new BigDecimal(0.0065))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (2000l <= combatValue && combatValue < 2250) {

			return baseBigDecimal.multiply(new BigDecimal(0.006))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (2250l <= combatValue && combatValue < 2500) {
			return baseBigDecimal.multiply(new BigDecimal(0.0055))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (2500l <= combatValue && combatValue < 2750) {

			return baseBigDecimal.multiply(new BigDecimal(0.005))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();

		}
		if (2750l <= combatValue && combatValue < 3000) {
			return baseBigDecimal.multiply(new BigDecimal(0.0045))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (3000l <= combatValue && combatValue < 3250) {
			return baseBigDecimal.multiply(new BigDecimal(0.004))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (3250l <= combatValue && combatValue < 3500) {
			return baseBigDecimal.multiply(new BigDecimal(0.0035))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		if (combatValue > 3500) {
			return baseBigDecimal.multiply(new BigDecimal(0.002))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
		return 0l;
	}
	/**
	 * 对战战斗力加成公式
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月15日 下午5:34:30
	 * @param winRightCount 胜利者答对题数量
	 * @param loseRightCount 失败者答对题数量
	 * @param winUseTime 胜利者用时
	 * @return
	 */
	public static Long battleCombatValueCompute(Long winRightCount,Long loseRightCount,Long winUseTime,Boolean  isDraw){
		Long timeAddition=0l;
		if(0l<=winUseTime&&winUseTime<40){
			timeAddition=14l;
		}
		if(40l<=winUseTime&&winUseTime<80){
			timeAddition=13l;
		}
		if(80l<=winUseTime&&winUseTime<120){
			timeAddition=12l;
		}
		if(120l<=winUseTime&&winUseTime<160){
			timeAddition=11l;
		}
		if(160l<=winUseTime&&winUseTime<200){
			timeAddition=10l;
		}
		if(200l<=winUseTime&&winUseTime<240){
			timeAddition=9l;
		}
		if(240l<=winUseTime&&winUseTime<280){
			timeAddition=8l;
		}
		if(280l<=winUseTime&&winUseTime<320){
			timeAddition=7l;
		}
		if(320l<=winUseTime&&winUseTime<360){
			timeAddition=6l;
		}
		if(360l<=winUseTime&&winUseTime<400){
			timeAddition=5l;
		}
		if(400l<=winUseTime&&winUseTime<440){
			timeAddition=4l;
		}
		if(440l<=winUseTime&&winUseTime<480){
			timeAddition=3l;
		}
		if(480l<=winUseTime&&winUseTime<520){
			timeAddition=2l;
		}
		if(520l<=winUseTime&&winUseTime<560){
			timeAddition=1l;
		}
		/*Long difference=winRightCount-loseRightCount;
		BigDecimal winBigDecimal=new BigDecimal(winRightCount).multiply(new BigDecimal(0.8));
		BigDecimal differenceBigDecimal=new BigDecimal(difference).multiply(new BigDecimal(0.4)).multiply(new BigDecimal(10));
		BigDecimal timeBigDecimal=new BigDecimal(timeAddition);
		if (isDraw) {
			return winBigDecimal.add(timeBigDecimal).longValue();
		}
		return winBigDecimal.add(differenceBigDecimal.add(timeBigDecimal)).longValue();*/
		/**
		 * 对战战斗力增量=（获胜者题目正确数量×0.8+（获胜者题目正确数量—失败者题目正确数量））×0.4×10+时间加成
		 * 
		 * @author  作者 : gaofengming
				E-mail : gaofengming@e-eduspace.com
		 * @date 创建时间   ：2016年8月26日下午1:26:01  
		 */
		Long difference=winRightCount-loseRightCount;
		//获胜者题目正确数量×0.8   这个需要四舍五入
		BigDecimal winBigDecimal=new BigDecimal(winRightCount).multiply(new BigDecimal(0.8)).setScale(2, BigDecimal.ROUND_HALF_UP);
		//（获胜者题目正确数量×0.8+（获胜者题目正确数量—失败者题目正确数量））
		BigDecimal addCount  = winBigDecimal.add(new BigDecimal(difference));
		//（获胜者题目正确数量×0.8）×0.4×10
		BigDecimal rawBigDecimal=winBigDecimal.multiply(new BigDecimal(0.4)).multiply(new BigDecimal(10));
		//对战战斗力增量=（获胜者题目正确数量×0.8+（获胜者题目正确数量—失败者题目正确数量））×0.4×10
		BigDecimal differenceBigDecimal=addCount.multiply(new BigDecimal(0.4)).multiply(new BigDecimal(10));
		//时间加成
		BigDecimal timeBigDecimal=new BigDecimal(timeAddition);
		
		if (isDraw) {
//			return rawBigDecimal.add(timeBigDecimal).longValue();
			//确保返回的数据是四舍五入的
			return rawBigDecimal.add(timeBigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
		}
//		return differenceBigDecimal.add(timeBigDecimal).longValue();
		return differenceBigDecimal.add(timeBigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
	}
}
