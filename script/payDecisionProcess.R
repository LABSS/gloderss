defaultWage <- 1000
benefit <- 0.01 * defaultWage
extortion <- 0.1 * defaultWage
punish <- 0.75 * defaultWage

collProb <- 0.9
statePun <- 0.5 * defaultWage

stateProtRep <- 0.3
stateFinderRep <- 0.05
mafiaPunishRep <- 0.7

atanFactor <- 0.01
x <- NULL
for(stateProtRep in seq(0,1,0.05)){
  for(stateFinderRep in seq(0,1,0.05)){
    for(mafiaPunishRep in seq(0,1,0.05)){
      TpayIG <- (benefit - extortion) - (statePun * stateFinderRep * (1 - collProb))
      TnotPayIG <- (extortion - benefit) - (punish * mafiaPunishRep)
      
      atanTpayIG <- 0.5 * (atan2(atanFactor * TpayIG, 1) / (pi / 2)) + 0.5
      atanTnotPayIG <- 0.5 * (atan2(atanFactor * TnotPayIG, 1) / (pi / 2)) + 0.5
      
      IG <- atanTpayIG / (atanTpayIG + atanTnotPayIG)
      
      x <- rbind(x, c(stateProtRep,stateFinderRep,mafiaPunishRep,IG))
    }
  }
}
