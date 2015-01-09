##
## Directory
##
base <- "/data/workspace/gloders/gloderss/output/"
filename <- "state-weak_norm-disabled_IO-disabled"

stateStatus <- c("state-weak","state-strong")
normStatus <- c("norm-disabled","norm-enabled-0.2-0.8","norm-enabled-0.5-0.5","norm-enabled-0.8-0.2")
ioStatus <- c("IO-disabled","IO-enabled")

table <- NULL
for(sStatus in stateStatus){
for(nStatus in normStatus){
for(iStatus in ioStatus){
      
filename <- paste(sStatus,"_",nStatus,"_",iStatus, sep="")
replica <- "/0"

##
## Load files
##
compensation <- read.csv(paste(base,filename,replica,"/compensation.csv", sep=""), header=TRUE, sep=";")
consumer <- read.csv(paste(base,filename,replica,"/consumer.csv", sep=""), header=TRUE, sep=";")
entrepreneur <- read.csv(paste(base,filename,replica,"/entrepreneur.csv", sep=""), header=TRUE, sep=";")
extortion <- read.csv(paste(base,filename,replica,"/extortion.csv", sep=""), header=TRUE, sep=";")
io <- read.csv(paste(base,filename,replica,"/intermediaryOrganization.csv", sep=""), header=TRUE, sep=";")
mafia <- read.csv(paste(base,filename,replica,"/mafia.csv", sep=""), header=TRUE, sep=";")
mafiosi <- read.csv(paste(base,filename,replica,"/mafiosi.csv", sep=""), header=TRUE, sep=";")
normative <- read.csv(paste(base,filename,replica,"/normative.csv", sep=""), header=TRUE, sep=";")
purchase <- read.csv(paste(base,filename,replica,"/purchase.csv", sep=""), header=TRUE, sep=";")
state <- read.csv(paste(base,filename,replica,"/state.csv", sep=""), header=TRUE, sep=";")

##
## Variables
##
nExtortion <- nrow(extortion)

nCustody <- nrow(subset(mafiosi, custodyTime > 0 & imprisonmentTime == 0))

nImprisonment <- nrow(subset(mafiosi, imprisonmentTime > 0))

nPaid <- nrow(subset(extortion, paid == "true"))

nNPaid <- nrow(subset(extortion, paid == "false"))

nDen <- nrow(subset(extortion, paid == "false" &
                      denouncedExtortion == "true" |
                      denouncedPunishment == "true"))

nNDen <- nrow(subset(extortion, paid == "false" &
                       (denouncedExtortion == "false" &
                          denouncedPunishment == "false")))

nInv <- nrow(subset(extortion, paid == "false" &
                      (investigatedExtortion == "true" ||
                         investigatedPunishment == "true")))

nInvCus <- nrow(subset(extortion, paid == "false" &
                         (investigatedExtortion == "true"  ||
                            investigatedPunishment == "true") &
                         mafiosoCustody == "true"))

nInvCon <- nrow(subset(extortion, paid == "false" &
                         (investigatedExtortion == "true"  ||
                            investigatedPunishment == "true") &
                         mafiosoConvicted == "true"))

nDenPun <- nrow(subset(compensation, denouncedPunishment == "true"))

nComp <- nrow(subset(compensation, denouncedPunishment == "true" &
                       stateCompensate == "true"))

##
## Calculation
##
propPaid <- nPaid / nExtortion
propDen <- nDen / nNPaid
propInv <- nInv / nDen
propCus <- nInvCus / nDen
propCon <- nInvCon / nDen
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
              propPaid, nPaid, propDen, nDen, propInv, nInv, 
              propCus, propCon, propComp,
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