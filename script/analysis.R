##
## Directory
##
base <- "/data/workspace/gloders/gloderss/output/"
filename <- "state-weak"
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
normative <- read.csv(paste(base,filename,replica,"/normative.csv", sep=""), header=TRUE, sep=";")
purchase <- read.csv(paste(base,filename,replica,"/purchase.csv", sep=""), header=TRUE, sep=";")
state <- read.csv(paste(base,filename,replica,"/state.csv", sep=""), header=TRUE, sep=";")

##
## Variables
##
nExtortion <- nrow(extortion)

lastExtortion <- extortion[nrow(extortion),]$time

nFreeMafiosi <- nrow(subset(mafia, custody == "false" &
                              imprisoned == "false"))

nCustodyMafiosi <- nrow(subset(mafia, custody == "true" &
                                 imprisoned == "false"))

nConvictedMafiosi <- nrow(subset(mafia, custody == "false" &
                                  imprisoned == "true"))

nMafiosi <- nrow(mafia)

nPaid <- nrow(subset(extortion, paid == "true"))

nNPaid <- nrow(subset(extortion, paid == "false"))

nDen <- nrow(subset(extortion, paid == "false" &
                      denouncedExtortion == "true" |
                      denouncedPunishment == "true"))

nNDen <- nrow(subset(extortion, paid == "false" &
                       (denouncedExtortion == "false" &
                          denouncedPunishment == "false")))

nInvExt <- nrow(subset(extortion, paid == "false" &
                         investigatedExtortion == "true"))

nInvExtCus <- nrow(subset(extortion, paid == "false" &
                            investigatedExtortion == "true" &
                            mafiosoCustody == "true"))

nInvExtCon <- nrow(subset(extortion, paid == "false" &
                            investigatedExtortion == "true" &
                            mafiosoConvicted == "true"))

nDenPun <- nrow(subset(compensation, denouncedPunishment == "true"))

nComp <- nrow(subset(compensation, denouncedPunishment == "true" &
                       stateCompensate == "true"))

##
## Calculation
##
propPaid <- nPaid / nExtortion
propDen <- nDen / nNPaid
propInvExt <- nInvExt / nDen
propCus <- nInvExtCus / nDen
propCon <- nInvExtCon / nDen
propComp <- nComp / nDenPun

mESalPay <- mean(entrepreneur$saliencePayExtortion)
sESalPay <- sd(entrepreneur$saliencePayExtortion)
mESalNPay <- mean(entrepreneur$salienceNotPayExtortion)
sESalNPay <- sd(entrepreneur$salienceNotPayExtortion)
mESalDen <- mean(entrepreneur$salienceDenounce)
sESalDen <- sd(entrepreneur$salienceDenonuce)
mESalNDen <- mean(entrepreneur$salienceNotDenounce)
sESalNDen <- sd(entrepreneur$salienceNotDenounce)

mCSalPay <- mean(consumer$saliencePayExtortion)
sCSalPay <- sd(consumer$saliencePayExtortion)
mCSalNPay <- mean(consumer$salienceNotPayExtortion)
sCSalNPay <- sd(consumer$salienceNotPayExtortion)
mCSalDen <- mean(consumer$salienceDenounce)
sCSalDen <- sd(consumer$salienceDenonuce)
mCSalNDen <- mean(consumer$salienceNotDenounce)
sCSalNDen <- sd(consumer$salienceNotDenounce)
mCSalBuyPE <- mean(consumer$salienceBuyPayingEntrepreneurs)
sCSalBuyPE <- sd(consumer$salienceBuyPayingEntrepreneurs)
mCSalBuyNPE <- mean(consumer$salienceBuyNotPayingEntrepreneurs)
sCSalBuyNPE <- sd(consumer$salienceBuyNotPayingEntrepreneurs)

##
## Proportion
##
prop <- cbind(nExtortion, lastExtortion,
              nFreeMafiosi, nCustodyMafiosi, nConvictedMafiosi,
              propPaid, nPaid, propDen, nDen, propInvExt, nInvExt, 
              propCus, propCon, propComp,
              mESalPay, sESalPay, mESalNPay, sESalNPay,
              mESalDen, sESalDen, mESalNDen, sESalNDen,
              mCSalPay, sCSalPay, mCSalNPay, sCSalNPay,
              mCSalDen, sCSalDen, mCSalNDen, sCSalNDen,
              mCSalBuyPE, sCSalBuyPE, mCSalBuyNPE, sCSalBuyNPE)

##
## Write
##
write.table(prop, file=paste(base,filename,replica,"/analisys.csv", sep=""),
            quote=FALSE, append=FALSE,sep=";", col.names=TRUE, row.names=FALSE)
