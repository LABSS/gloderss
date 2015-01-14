##
## Directory
##
base <- "/data/workspace/gloders/gloderss/output/"
dir <- "state-strong_norm-enabled-0.2-0.8_IO-enabled"

stateStatus <- c("state-weak","state-strong")
normStatus <- c("norm-disabled","norm-enabled-0.2-0.8","norm-enabled-0.5-0.5","norm-enabled-0.8-0.2")
ioStatus <- c("IO-disabled","IO-enabled")

table <- NULL
for(sStatus in stateStatus){
for(nStatus in normStatus){
for(iStatus in ioStatus){
      
dir <- paste(sStatus,"_",nStatus,"_",iStatus, sep="")
replica <- "/0"

##
## Load files
##
compensation <- try(read.csv(paste(base,dir,replica,"/compensation.csv", sep=""), header=TRUE, sep=";"))
if (inherits(compensation, 'try-error')) { compensation <- NULL }

consumer <- try(read.csv(paste(base,dir,replica,"/consumer.csv", sep=""), header=TRUE, sep=";"))
if (inherits(consumer, 'try-error')) { consumer <- NULL }

entrepreneur <- try(read.csv(paste(base,dir,replica,"/entrepreneur.csv", sep=""), header=TRUE, sep=";"))
if (inherits(entrepreneur, 'try-error')) { entrepreneur <- NULL }

extortion <- try(read.csv(paste(base,dir,replica,"/extortion.csv", sep=""), header=TRUE, sep=";"))
if (inherits(extortion, 'try-error')) { extortion <- NULL }

#io <- try(read.csv(paste(base,dir,replica,"/intermediaryOrganization.csv", sep=""), header=TRUE, sep=";"))
#if (inherits(io, 'try-error')) { io <- NULL }

mafia <- try(read.csv(paste(base,dir,replica,"/mafia.csv", sep=""), header=TRUE, sep=";"))
if (inherits(mafia, 'try-error')) { mafia <- NULL }

mafiosi <- try(read.csv(paste(base,dir,replica,"/mafiosi.csv", sep=""), header=TRUE, sep=";"))
if (inherits(mafiosi, 'try-error')) { mafiosi <- NULL }
  
normative <- try(read.csv(paste(base,dir,replica,"/normative.csv", sep=""), header=TRUE, sep=";"))
if (inherits(normative, 'try-error')) { normative <- NULL }

purchase <- try(read.csv(paste(base,dir,replica,"/purchase.csv", sep=""), header=TRUE, sep=";"))
if (inherits(purchase, 'try-error')) { purchase <- NULL }

state <- try(read.csv(paste(base,dir,replica,"/state.csv", sep=""), header=TRUE, sep=";"))
if (inherits(state, 'try-error')) { state <- NULL }

##
## Variables
##

if (!is.null(mafiosi)) {
  nCustody <- nrow(subset(mafiosi, custodyTime > 0 & imprisonmentTime == 0))
  nImprisonment <- nrow(subset(mafiosi, imprisonmentTime > 0))
} else {
  nCustody <- 0
  nImprisonment <- 0
}

if (!is.null(extortion)) {
  nExtortion <- nrow(extortion)
  nPaid <- nrow(subset(extortion, paid == "true"))
  nNPaid <- nrow(subset(extortion, paid == "false"))
  nDenExt <- nrow(subset(extortion, paid == "false" &
                           denouncedExtortion == "true"))
  nNDenExt <- nrow(subset(extortion, paid == "false" &
                            denouncedExtortion == "false"))
  nDenPun <- nrow(subset(extortion, paid == "false" &
                           denouncedPunishment == "true"))
  nNDenPun <- nrow(subset(extortion, paid == "false" &
                            denouncedExtortion == "false"))
  nInv <- nrow(subset(extortion, paid == "false" &
                      (investigatedExtortion == "true" |
                         investigatedPunishment == "true")))
  nInvCus <- nrow(subset(extortion, paid == "false" &
                         (investigatedExtortion == "true"  |
                            investigatedPunishment == "true") &
                         mafiosoCustody == "true"))
  nInvCon <- nrow(subset(extortion, paid == "false" &
                         (investigatedExtortion == "true"  |
                            investigatedPunishment == "true") &
                         mafiosoConvicted == "true"))
} else {
  nExtortion <- 0
  nPaid <- 0
  nNPaid <- 0
  nDen <- 0
  nNDen <- 0
  nInv <- 0
  nInvCus <- 0
  nInvCon <- 0
}

if (!is.null(compensation)) {
  nDenPun <- nrow(subset(compensation, denouncedPunishment == "true"))
  nComp <- nrow(subset(compensation, denouncedPunishment == "true" &
                       stateCompensate == "true"))
} else {
  nDenPun <- 0
  nComp <- 0
}

##
## Calculation
##
propPaid <- nPaid / nExtortion
propDenExt <- nDenExt / nExtortion
propDenPun <- nDenPun / nNPaid
propInv <- nInv / (nDenExt + nDenPun)
propCus <- nInvCus / (nDenExt + nDenPun)
propCon <- nInvCon / (nDenExt + nDenPun)
propComp <- nComp / nDenPun

mESalPay <- mean(entrepreneur$saliencePayExtortion)
sESalPay <- sd(entrepreneur$saliencePayExtortion)
mESalNPay <- mean(entrepreneur$salienceNotPayExtortion)
sESalNPay <- sd(entrepreneur$salienceNotPayExtortion)
mESalDen <- mean(entrepreneur$salienceDenounce)
sESalDen <- sd(entrepreneur$salienceDenounce)
mESalNDen <- mean(entrepreneur$salienceNotDenounce)
sESalNDen <- sd(entrepreneur$salienceNotDenounce)

mCSalPay <- mean(consumer$saliencePayExtortion)
sCSalPay <- sd(consumer$saliencePayExtortion)
mCSalNPay <- mean(consumer$salienceNotPayExtortion)
sCSalNPay <- sd(consumer$salienceNotPayExtortion)
mCSalDen <- mean(consumer$salienceDenounce)
sCSalDen <- sd(consumer$salienceDenounce)
mCSalNDen <- mean(consumer$salienceNotDenounce)
sCSalNDen <- sd(consumer$salienceNotDenounce)
mCSalBuyPE <- mean(consumer$salienceBuyPayingEntrepreneurs)
sCSalBuyPE <- sd(consumer$salienceBuyPayingEntrepreneurs)
mCSalBuyNPE <- mean(consumer$salienceBuyNotPayingEntrepreneurs)
sCSalBuyNPE <- sd(consumer$salienceBuyNotPayingEntrepreneurs)

##
## Proportion
##
prop <- cbind(sStatus, nStatus, iStatus,
              nExtortion, nCustody, nImprisonment,
              propPaid, nPaid, propDenExt, nDenExt, propDenPun, nDenPun,
              propInv, nInv, propCus, propCon, propComp,
              mESalPay, sESalPay, mESalNPay, sESalNPay,
              mESalDen, sESalDen, mESalNDen, sESalNDen,
              mCSalPay, sCSalPay, mCSalNPay, sCSalNPay,
              mCSalDen, sCSalDen, mCSalNDen, sCSalNDen,
              mCSalBuyPE, sCSalBuyPE, mCSalBuyNPE, sCSalBuyNPE)

table <- rbind(table, prop)
}
}
}

##
## Write
##
write.table(table, file=paste(base,"/summary.csv", sep=""),
            quote=FALSE, append=FALSE,sep=";", col.names=TRUE, row.names=FALSE)

