defaultWage <- 750
benefit <- 0.1 * defaultWage
extortion <- 0.3 * defaultWage
punish <- 0.4 * defaultWage

collProb <- 0.9
statePun <- 0.5 * defaultWage

stateProtRep <- 0.3
stateFinderRep <- 0.05
mafiaPunishRep <- 0.7

TpayIG <- (benefit - extortion) - (statePun * stateFinderRep * (1 - collProb))
TnotPayIG <- (extortion - benefit) - (punish * mafiaPunishRep)

TpayIG <- ((atan(TpayIG) * (180 / pi)) + 360) %% 360
TnotPayIG <- ((atan(TnotPayIG) * (180 / pi)) + 360) %% 360

IG <- TpayIG / (TpayIG + TnotPayIG)