library(ggplot2)
library(data.table)
##
## Directory
##
base <- "/data/workspace/gloders/gloderss/output"

dir <- c("Snpun-nnorm-Mhard-IOinactive/0",
         "Snpun-nnorm-Mhard-IOinactive/20",
         "Snpun-nnorm-Mhard-IOinactive/40",
         "Snpun-nnorm-Mhard-IOinactive/50",
         "Snpun-nnorm-Mhard-IOinactive/60",
         "Snpun-nnorm-Mhard-IOinactive/80",
         "Snpun-nnorm-Mhard-IOactive/0",
         "Snpun-nnorm-Mhard-IOactive/20",
         "Snpun-nnorm-Mhard-IOactive/40",
         "Snpun-nnorm-Mhard-IOactive/50",
         "Snpun-nnorm-Mhard-IOactive/60",
         "Snpun-nnorm-Mhard-IOactive/80",
         "Snpun-nnorm-Msoft-IOinactive/0",
         "Snpun-nnorm-Msoft-IOinactive/20",
         "Snpun-nnorm-Msoft-IOinactive/40",
         "Snpun-nnorm-Msoft-IOinactive/50",
         "Snpun-nnorm-Msoft-IOinactive/60",
         "Snpun-nnorm-Msoft-IOinactive/80",
         "Snpun-nnorm-Msoft-IOactive/0",
         "Snpun-nnorm-Msoft-IOactive/20",
         "Snpun-nnorm-Msoft-IOactive/40",
         "Snpun-nnorm-Msoft-IOactive/50",
         "Snpun-nnorm-Msoft-IOactive/60",
         "Snpun-nnorm-Msoft-IOactive/80",
         "Spun-norm-Mhard-IOinactive/0",
         "Spun-norm-Mhard-IOinactive/20",
         "Spun-norm-Mhard-IOinactive/40",
         "Spun-norm-Mhard-IOinactive/50",
         "Spun-norm-Mhard-IOinactive/60",
         "Spun-norm-Mhard-IOinactive/80",
         "Spun-norm-Mhard-IOactive/0",
         "Spun-norm-Mhard-IOactive/20",
         "Spun-norm-Mhard-IOactive/40",
         "Spun-norm-Mhard-IOactive/50",
         "Spun-norm-Mhard-IOactive/60",
         "Spun-norm-Mhard-IOactive/80",
         "Spun-norm-Msoft-IOinactive/0",
         "Spun-norm-Msoft-IOinactive/20",
         "Spun-norm-Msoft-IOinactive/40",
         "Spun-norm-Msoft-IOinactive/50",
         "Spun-norm-Msoft-IOinactive/60",
         "Spun-norm-Msoft-IOinactive/80",
         "Spun-norm-Msoft-IOactive/0",
         "Spun-norm-Msoft-IOactive/20",
         "Spun-norm-Msoft-IOactive/40",
         "Spun-norm-Msoft-IOactive/50",
         "Spun-norm-Msoft-IOactive/60",
         "Spun-norm-Msoft-IOactive/80",
         "Spun-nnorm-Mhard-IOinactive/0",
         "Spun-nnorm-Mhard-IOinactive/20",
         "Spun-nnorm-Mhard-IOinactive/40",
         "Spun-nnorm-Mhard-IOinactive/50",
         "Spun-nnorm-Mhard-IOinactive/60",
         "Spun-nnorm-Mhard-IOinactive/80",
         "Spun-nnorm-Mhard-IOactive/0",
         "Spun-nnorm-Mhard-IOactive/20",
         "Spun-nnorm-Mhard-IOactive/40",
         "Spun-nnorm-Mhard-IOactive/50",
         "Spun-nnorm-Mhard-IOactive/60",
         "Spun-nnorm-Mhard-IOactive/80",
         "Spun-nnorm-Msoft-IOinactive/0",
         "Spun-nnorm-Msoft-IOinactive/20",
         "Spun-nnorm-Msoft-IOinactive/40",
         "Spun-nnorm-Msoft-IOinactive/50",
         "Spun-nnorm-Msoft-IOinactive/60",
         "Spun-nnorm-Msoft-IOinactive/80",
         "Spun-nnorm-Msoft-IOactive/0",
         "Spun-nnorm-Msoft-IOactive/20",
         "Spun-nnorm-Msoft-IOactive/40",
         "Spun-nnorm-Msoft-IOactive/50",
         "Spun-nnorm-Msoft-IOactive/60",
         "Spun-nnorm-Msoft-IOactive/80")

dir <- c("S1-before-1980/0",
         "S2-1980-1990/0",
         "S3-1990-1995/0",
         "S4-1995-2000/0",
         "S5-after-2000/0")

dir <- "S-before-1980-after-2000"

replicas <- 0:0

xvaxis <- c(1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,3,3,4,4,4,4,4,4,
           5,5,5,5,5,5,6,6,6,6,6,6,7,7,7,7,7,7,8,8,8,8,8,8,
           9,9,9,9,9,9,10,10,10,10,10,10,11,11,11,11,11,11,
           12,12,12,12,12,12)

xaxis <- c(1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6,
           1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6,
           1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6)

simLen <- 50000
delta <- 100
con <- 200
ent <- 100

compensation <- NULL
compensations <- NULL
consumer <- NULL
consumers <- NULL
entrepreneur <- NULL
entrepreneurs <- NULL
extortion <- NULL
extortions <- NULL
io <- NULL
ios <- NULL
mafia <- NULL
mafias <- NULL
mafiosi <- NULL
mafiosis <- NULL
normative <- NULL
normatives <- NULL
purchase <- NULL
purchases <- NULL
state <- NULL
states <- NULL

nCustody <- NULL
nImprisonment <- NULL
nExtortion <- NULL
nPaid <- NULL
nNPaid <- NULL
nDenExt <- NULL
nNDenExt <- NULL
nDenPun <- NULL
nNDenPun <- NULL
nPun <- NULL
nInv <- NULL
nInvCus <- NULL
nInvCon <- NULL
nDen <- NULL
nComp <- NULL

nAffiliated <- NULL

propPaid <- NULL
propDenExt <- NULL
propDenPun <- NULL
propInv <- NULL
propCus <- NULL
propCon <- NULL
propComp <- NULL
propCompleted <- NULL
propNDen <- NULL
propPunNPay <- NULL
propPun <- NULL

mESalPay <- NULL
sESalPay <- NULL
mESalNPay <- NULL
sESalNPay <- NULL
mESalDen <- NULL
sESalDen <- NULL
mESalNDen <- NULL
sESalNDen <- NULL

mCSalPay <- NULL
sCSalPay <- NULL
mCSalNPay <- NULL
sCSalNPay <- NULL
mCSalDen <- NULL
sCSalDen <- NULL
mCSalNDen <- NULL
sCSalNDen <- NULL
mCSalBuyPE <- NULL
sCSalBuyPE <- NULL
mCSalBuyNPE <- NULL
sCSalBuyNPE <- NULL

prop <- NULL

dirs <- 1:length(dir)
nReplicas <- length(replicas)
for(i in dirs){
  
  compensations <- NULL
  consumers <- NULL
  entrepreneurs <- NULL
  extortions <- NULL
  ios <- NULL
  mafias <- NULL
  mafiosis <- NULL
  normatives <- NULL
  purchases <- NULL
  states<- NULL
  
  for(replica in replicas){

    ##
    ## Compensation
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/compensation.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      compensations <- rbind(compensations, aux)
    }
    
    ##
    ## Consumer
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/consumer.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      consumers <- rbind(consumers, aux)
    }
    
    ##
    ## Entrepreneur
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/entrepreneur.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      entrepreneurs <- rbind(entrepreneurs, aux)
    }
    
    ##
    ## Extortion
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/extortion.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      extortions <- rbind(extortions, aux)
    }
    
    ##
    ## Intermediary Organization
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/intermediaryOrganization.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      ios <- rbind(ios, aux)
    }
    
    ##
    ## Mafia
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/mafia.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      mafias <- rbind(mafias, aux)
    }
    
    ##
    ## Mafiosi
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/mafiosi.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      mafiosis <- rbind(mafiosis, aux)
    }
    
    ##
    ## Normative
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/normative.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      normatives <- rbind(normatives, aux)
    }
    
    ##
    ## Purchase
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/purchase.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      purchases <- rbind(purchases, aux)
    }
    
    ##
    ## State
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/state.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      states <- rbind(states, aux)
    }
  }
  
  compensation[[i]] <- compensations
  consumer[[i]] <- consumers
  entrepreneur[[i]] <- entrepreneurs
  extortion[[i]] <- extortions
  io[[i]] <- ios
  mafia[[i]] <- mafias
  mafiosi[[i]] <- mafiosis
  normative[[i]] <- normatives
  purchase[[i]] <- purchases
  state[[i]] <- states

  ##
  ## Variables
  ##
  if (!is.null(mafiosi[[i]])) {
    nCustody[[i]] <- nrow(subset(mafiosi[[i]], custodyTime > 0 & imprisonmentTime == 0)) / nReplicas
    nImprisonment[[i]] <- nrow(subset(mafiosi[[i]], imprisonmentTime > 0)) / nReplicas
  } else {
    nCustody[[i]] <- 0
    nImprisonment[[i]] <- 0
  }

  if (!is.null(extortion[[i]])) {
    nExtortion[[i]] <- nrow(extortion[[i]]) / nReplicas
    nPaid[[i]] <- nrow(subset(extortion[[i]], paid == "true")) / nReplicas
    nNPaid[[i]] <- nrow(subset(extortion[[i]], paid == "false")) / nReplicas
    nDenExt[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                                  denouncedExtortion == "true")) / nReplicas
    nNDenExt[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                                   denouncedExtortion == "false")) / nReplicas
    nDenPun[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                                  mafiaPunished == "true" &
                                  denouncedPunishment == "true")) / nReplicas
    nNDenPun[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                                   mafiaPunished == "true" &
                                   denouncedExtortion == "false")) / nReplicas
    nInv[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                               (investigatedExtortion == "true" |
                                  investigatedPunishment == "true") &
                               (denouncedExtortion == "true" |
                                  denouncedPunishment == "true"))) / nReplicas
    nInvCus[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                                  (investigatedExtortion == "true"  |
                                     investigatedPunishment == "true") &
                                  mafiosoCustody == "true")) / nReplicas
    nInvCon[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                                  (investigatedExtortion == "true"  |
                                     investigatedPunishment == "true") &
                                  mafiosoConvicted == "true")) / nReplicas
    nDen[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                               (denouncedExtortion == "true" |
                                  denouncedPunishment == "true"))) / nReplicas
    nPun[[i]] <- nrow(subset(extortion[[i]], paid == "false" &
                               mafiaPunished == "true")) / nReplicas
  } else {
    nExtortion[[i]] <- 0
    nPaid[[i]] <- 0
    nNPaid[[i]] <- 0
    nDen[[i]] <- 0
    nNDen[[i]] <- 0
    nInv[[i]] <- 0
    nInvCus[[i]] <- 0
    nInvCon[[i]] <- 0
    nDen[[i]] <- 0
    nPun[[i]] <- 0
  }

  if (!is.null(compensation[[i]])) {
    nComp[[i]] <- nrow(subset(compensation[[i]], denouncedPunishment == "true" &
                                stateCompensate == "true")) / nReplicas
  } else {
    nComp[[i]] <- 0
  }

  nAffiliated[[i]] <- nrow(subset(entrepreneur[[i]], affiliated == "true")) / nReplicas

  ##
  ## Calculation
  ##
  propPaid[[i]] <- nPaid[[i]] / nExtortion[[i]]
  propDenExt[[i]] <- nDenExt[[i]] / nExtortion[[i]]
  propDenPun[[i]] <- nDenPun[[i]] / nNPaid[[i]]
  propInv[[i]] <- nInv[[i]] / (nDenExt[[i]] + nDenPun[[i]])
  propCus[[i]] <- nInvCus[[i]] / (nDenExt[[i]] + nDenPun[[i]])
  propCon[[i]] <- nInvCon[[i]] / (nDenExt[[i]] + nDenPun[[i]])
  propComp[[i]] <- nComp[[i]] / nDenPun[[i]]
  propPun[[i]] <- nPun[[i]] / nExtortion[[i]]
  propPunNPay[[i]] <- nPun[[i]] / nNPaid[[i]]
  
  propCompleted[[i]] <- nInvCon[[i]] / nInv[[i]]
  
  propNDen[[i]] <- 1 - (nDen[[i]] / nExtortion[[i]])
  
  mESalPay[[i]] <- mean(entrepreneur[[i]]$saliencePayExtortion)
  sESalPay[[i]] <- sd(entrepreneur[[i]]$saliencePayExtortion)
  mESalNPay[[i]] <- mean(entrepreneur[[i]]$salienceNotPayExtortion)
  sESalNPay[[i]] <- sd(entrepreneur[[i]]$salienceNotPayExtortion)
  mESalDen[[i]] <- mean(entrepreneur[[i]]$salienceDenounce)
  sESalDen[[i]] <- sd(entrepreneur[[i]]$salienceDenounce)
  mESalNDen[[i]] <- mean(entrepreneur[[i]]$salienceNotDenounce)
  sESalNDen[[i]] <- sd(entrepreneur[[i]]$salienceNotDenounce)
  
  mCSalPay[[i]] <- mean(consumer[[i]]$saliencePayExtortion)
  sCSalPay[[i]] <- sd(consumer[[i]]$saliencePayExtortion)
  mCSalNPay[[i]] <- mean(consumer[[i]]$salienceNotPayExtortion)
  sCSalNPay[[i]] <- sd(consumer[[i]]$salienceNotPayExtortion)
  mCSalDen[[i]] <- mean(consumer[[i]]$salienceDenounce)
  sCSalDen[[i]] <- sd(consumer[[i]]$salienceDenounce)
  mCSalNDen[[i]] <- mean(consumer[[i]]$salienceNotDenounce)
  sCSalNDen[[i]] <- sd(consumer[[i]]$salienceNotDenounce)
  mCSalBuyPE[[i]] <- mean(consumer[[i]]$salienceBuyPayingEntrepreneurs)
  sCSalBuyPE[[i]] <- sd(consumer[[i]]$salienceBuyPayingEntrepreneurs)
  mCSalBuyNPE[[i]] <- mean(consumer[[i]]$salienceBuyNotPayingEntrepreneurs)
  sCSalBuyNPE[[i]] <- sd(consumer[[i]]$salienceBuyNotPayingEntrepreneurs)

  ##
  ## Proportion
  ##
  prop[[i]] <- cbind(nExtortion[[i]], nCustody[[i]], nImprisonment[[i]],
                     propPaid[[i]], nPaid[[i]], propDenExt[[i]], nDenExt[[i]],
                     propDenPun[[i]], nDenPun[[i]],propPun[[i]],propPunNPay[[i]],
                     propInv[[i]], nInv[[i]], propCus[[i]], propCon[[i]], propComp[[i]],
                     mESalPay[[i]], sESalPay[[i]], mESalNPay[[i]], sESalNPay[[i]],
                     mESalDen[[i]], sESalDen[[i]], mESalNDen[[i]], sESalNDen[[i]],
                     mCSalPay[[i]], sCSalPay[[i]], mCSalNPay[[i]], sCSalNPay[[i]],
                     mCSalDen[[i]], sCSalDen[[i]], mCSalNDen[[i]], sCSalNDen[[i]],
                     mCSalBuyPE[[i]], sCSalBuyPE[[i]], mCSalBuyNPE[[i]], sCSalBuyNPE[[i]])
}

##
## Write
##
write.table(prop[[i]], file=paste0(base,dir[i],"/summary.csv"),
            quote=FALSE, append=FALSE, sep=";", col.names=TRUE, row.names=FALSE)

write.table(prop[[i]], file=paste0(base,"/summary.csv"),
            quote=FALSE, append=TRUE, sep=";", col.names=FALSE, row.names=FALSE)

##
## Histogram of the Pay Extortion salience
##
png(filename=paste0(base,dir[i],"/salPayExtortion.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=saliencePayExtortion)) +
  xlim(0,1) + ylim(0,30) +
  ylab('Number of Entrepreneurs') + xlab('Salience Pay Extortion') +
  geom_histogram(aes(y=..density..),
                 binwidth=.05, colour="black", fill="white", size=1.5) +
  geom_vline(aes(xintercept=mean(saliencePayExtortion, na.rm=T)),
             color="red", linetype="dashed", size=1.5) +
  geom_density(alpha=.05, fill="black", linetype="dotted", size=1.0) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of the Do Not Pay Extortion salience
##
png(filename=paste0(base,dir[i],"/salNotPayExtortion.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceNotPayExtortion)) +
  xlim(0,1) + ylim(0,30) +
  ylab('Number of Entrepreneurs') + xlab('Salience Do Not Pay Extortion') +
  geom_histogram(aes(y=..density..),
                 binwidth=.05, colour="black", fill="white", size=1.5) +
  geom_vline(aes(xintercept=mean(salienceNotPayExtortion, na.rm=T)),
             color="red", linetype="dashed", size=1.5) +
  geom_density(alpha=.05, fill="black", linetype="dotted", size=1.0) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of the Do Not Denounce Extortion salience
##
png(filename=paste0(base,dir[i],"/salNotDenounce.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceNotDenounce)) +
  xlim(0,1) + ylim(0,30) +
  ylab('Number of Entrepreneurs') + xlab('Salience Do Not Denounce Extortion') +
  geom_histogram(aes(y=..density..),
                 binwidth=.05, colour="black", fill="white", size=1.5) +
  geom_vline(aes(xintercept=mean(salienceNotDenounce, na.rm=T)),
             color="red", linetype="dashed", size=1.5) +
  geom_density(alpha=.05, fill="black", linetype="dotted", size=1.0) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of the Denounce Extortion salience
##
png(filename=paste0(base,dir[i],"/salDenounce.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceDenounce)) +
  xlim(0,1) + ylim(0,30) +
  ylab('Number of Entrepreneurs') + xlab('Salience Denounce Extortion') +
  geom_histogram(aes(y=..density..),
                 binwidth=.05, colour="black", fill="white", size=1.5) +
  geom_vline(aes(xintercept=mean(salienceDenounce, na.rm=T)),
             color="red", linetype="dashed", size=1.5) +
  geom_density(alpha=.05, fill="black", linetype="dotted", size=1.0) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of Number of Paid Extortion
##
png(filename=paste0(base,dir[i],"/histPaidExtortion.png"), width=1024, height=768)
ggplot(extortion[[i]][extortion[[i]]$paid == "true",], aes(x=time)) +
  xlim(0,simLen) + ylim(0,700) +
  ylab('Number of Paid Extortion') + xlab('Time') +
  geom_histogram(aes(y=..count..),
                 binwidth=500, colour="black", fill="grey", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of Number of Non-Paid Extortion
##
png(filename=paste0(base,dir[i],"/histNotPaidExtortion.png"), width=1024, height=768)
ggplot(extortion[[i]][extortion[[i]]$paid == "false",], aes(x=time)) +
  xlim(0,simLen) + ylim(0,400) +
  ylab('Number of Non-Paid Extortion') + xlab('Time') +
  geom_histogram(aes(y=..count..),
                 binwidth=500, colour="black", fill="grey", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of Number of Extortion
##
png(filename=paste0(base,dir[i],"/histExtortion.png"), width=1024, height=768)
ggplot(extortion[[i]], aes(x=time)) +
  xlim(0,simLen) + ylim(0,600) +
  ylab('Number of Extortion') + xlab('Time') +
  geom_histogram(aes(y=..count..),
                 binwidth=500, colour="black", fill="grey", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of Number of Denounce
##
png(filename=paste0(base,dir[i],"/histDenounce.png"), width=1024, height=768)
ggplot(extortion[[i]][extortion[[i]]$denouncedExtortion == "true" |
                        extortion[[i]]$denouncedPunishment == "true",], aes(x=time)) +
  xlim(0,simLen) + ylim(0,55) +
  ylab('Number of Denounces') + xlab('Time') +
  geom_histogram(aes(y=..count..),
                 binwidth=500, colour="black", fill="grey", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of Number of Denounced Extortion
##
png(filename=paste0(base,dir[i],"/histDenExt.png"), width=1024, height=768)
ggplot(extortion[[i]][extortion[[i]]$denouncedExtortion == "true",], aes(x=time)) +
  xlim(0,simLen) + ylim(0,55) +
  ylab('Number of Denounced Extortion') + xlab('Time') +
  geom_histogram(aes(y=..count..),
                 binwidth=500, colour="black", fill="grey", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Histogram of Number of Denounced Punishment
##
png(filename=paste0(base,dir[i],"/histDenPun.png"), width=1024, height=768)
ggplot(extortion[[i]][extortion[[i]]$denouncedPunishment == "true",], aes(x=time)) +
  xlim(0,simLen) + ylim(0,55) +
  ylab('Number of Denounced Punishment') + xlab('Time') +
  geom_histogram(aes(y=..count..),
                 binwidth=500, colour="black", fill="grey", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

##
## Scatterplot of Extortions
##
data <- data.table(cbind(dir,xaxis,xvaxis,nExtortion))
png(filename=paste0(base,"/numExtortion.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis, y=as.numeric(as.character(nExtortion)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('Number of Extortions') + ylim(0,2900) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        #legend.position = 'none')
dev.off()

#legend.title = element_blank(),
#legend.text = element_text(colour="black", size=24, face="bold"))

##
## Scatterplot of Paid of Extortion
##
data <- data.table(cbind(dir,xaxis,xvaxis,nPaid))
png(filename=paste0(base,"/numPaidExt.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(nPaid)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('Number of Paid Extortions') + ylim(0,20200) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        #legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Paid of Extortions
##
data <- data.table(cbind(dir,xaxis,xvaxis,propPaid))
png(filename=paste0(base,"/propPaidExt.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(propPaid)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('% Paid Extortions') + ylim(0,1) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        # legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Punishment general
##
data <- data.table(cbind(dir,xaxis,xvaxis,propPun))
png(filename=paste0(base,"/propPun.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(propPun)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('% Punishments') + ylim(0,1) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Punishment per No Payment
##
data <- data.table(cbind(dir,xaxis,xvaxis,propPunNPay))
png(filename=paste0(base,"/propPunNPay.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(propPunNPay)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('% Punishments') + ylim(0,1) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Denounce
##
data <- data.table(cbind(dir,xaxis,xvaxis,nDen))
png(filename=paste0(base,"/numDenounce.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(nDen)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('Number of Denounces') + ylim(0,350) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        # legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Denounces of Extortion
##
data <- data.table(cbind(dir,xaxis,xvaxis,propDenExt))
png(filename=paste0(base,"/propDenExt.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(propDenExt)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('% Denounced Extortions') + ylim(0,1) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        # legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Denounces of Punishments
##
data <- data.table(cbind(dir,xaxis,xvaxis,propDenPun))
png(filename=paste0(base,"/propDenPun.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(propDenPun)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('% Denounces Punishments') + ylim(0,1) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 0.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=0.5, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=0.5, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        # legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Denounces
##
propDen <- propDenExt + propDenPun
data <- data.table(cbind(dir,xaxis,xvaxis,propDen))
png(filename=paste0(base,"/propDen.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(propDen)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('% Denounces') + ylim(0,1) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        # legend.position = 'none')
dev.off()

##
## Scatterplot of Imprisonment
##
data <- data.table(cbind(dir,xaxis,xvaxis,nInvCon))
png(filename=paste0(base,"/numImprisonment.png"), width=1024, height=768)
ggplot(data, aes(x=xaxis,y=as.numeric(as.character(nInvCon)),
                 group=xvaxis, color=as.character(xvaxis))) +
  xlab('') + ylab('Number Imprisonment') + ylim(0,100) +
  geom_line() +
  geom_point(aes(color=as.character(xvaxis))) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        # legend.position = 'none')
dev.off()

##
## Norms' set shift
##
for(i in 1:dirs){
  new <- nrow(entrepreneur[[i]][(entrepreneur[[i]]$salienceDenounce >= 
                                   entrepreneur[[i]]$salienceNotDenounce) &
                                  (entrepreneur[[i]]$saliencePayExtortion < 
                                     entrepreneur[[i]]$salienceNotPayExtortion),])
  
  traditional <- nrow(entrepreneur[[i]][(entrepreneur[[i]]$salienceDenounce <
                                           entrepreneur[[i]]$salienceNotDenounce) &
                                          (entrepreneur[[i]]$saliencePayExtortion >=
                                             entrepreneur[[i]]$salienceNotPayExtortion),])
  
  onlyNotPay <- nrow(entrepreneur[[i]][(entrepreneur[[i]]$saliencePayExtortion <= 
                                          entrepreneur[[i]]$salienceNotPayExtortion),]) - new
  
  onlyDenounce <- nrow(entrepreneur[[i]][(entrepreneur[[i]]$salienceDenounce >= 
                                            entrepreneur[[i]]$salienceNotDenounce),]) - new
  
  print(c(i, new, traditional, onlyNotPay, onlyDenounce, (new+traditional+onlyNotPay+onlyDenounce)))
}

##
## Norms' set shift
##
n <- c(0,100,200,250,300,400)
for(i in 1:dirs){
  aux <- data.table(entrepreneur[[i]])
  new <- nrow(aux[which((time == simLen) &
                          (saliencePayExtortion <= salienceNotPayExtortion) &
                          (salienceDenounce >= salienceNotDenounce)),])
  
  traditional <- nrow(aux[which((time == simLen) &
                                  (saliencePayExtortion > salienceNotPayExtortion) &
                                  (salienceDenounce < salienceNotDenounce)),])
      
  onlyNotPay <- nrow(aux[which((time == simLen) &
                                 (saliencePayExtortion <= salienceNotPayExtortion) &
                                 (salienceDenounce < salienceNotDenounce)),])
      
  onlyDenounce <- nrow(aux[which((time == simLen) &
                                   (saliencePayExtortion > salienceNotPayExtortion) &
                                   (salienceDenounce >= salienceNotDenounce)),])
      
  print(c(i, traditional, onlyNotPay, onlyDenounce, new, (new+traditional+onlyNotPay+onlyDenounce)))
  z <- i %% 6
  if(z == 0){
    z <- 6
  }
  print(c(i, (((ent-n[z])-traditional)/ent), onlyNotPay/ent, onlyDenounce/ent, ((new-n[z])/ent)))
}

##
## Validation Information
##
city <- c("Agrigento",
          "Catania",
          "Messina",
          "Siracusa",
          "01-before1980",
          "02-1980-1990",
          "03-1990-2000-10",
          "03-1990-2000-20",
          "03-1990-2000-40",
          "03-1990-2000-50",
          "03-1990-2000-60",
          "03-1990-2000-80",
          "04-after2000-10",
          "04-after2000-20",
          "04-after2000-40",
          "04-after2000-50",
          "04-after2000-60",
          "04-after2000-80")

city <- c("Agrigento",
          "Catania",
          "Messina",
          "Siracusa",
          "Snpun-nnorm-Mhard-IOinactive/0",
          "Snpun-nnorm-Mhard-IOinactive/20",
          "Snpun-nnorm-Mhard-IOinactive/40",
          "Snpun-nnorm-Mhard-IOinactive/50",
          "Snpun-nnorm-Mhard-IOinactive/60",
          "Snpun-nnorm-Mhard-IOinactive/80",
          "Snpun-nnorm-Mhard-IOactive/0",
          "Snpun-nnorm-Mhard-IOactive/20",
          "Snpun-nnorm-Mhard-IOactive/40",
          "Snpun-nnorm-Mhard-IOactive/50",
          "Snpun-nnorm-Mhard-IOactive/60",
          "Snpun-nnorm-Mhard-IOactive/80",
          "Snpun-nnorm-Msoft-IOinactive/0",
          "Snpun-nnorm-Msoft-IOinactive/20",
          "Snpun-nnorm-Msoft-IOinactive/40",
          "Snpun-nnorm-Msoft-IOinactive/50",
          "Snpun-nnorm-Msoft-IOinactive/60",
          "Snpun-nnorm-Msoft-IOinactive/80",
          "Snpun-nnorm-Msoft-IOactive/0",
          "Snpun-nnorm-Msoft-IOactive/20",
          "Snpun-nnorm-Msoft-IOactive/40",
          "Snpun-nnorm-Msoft-IOactive/50",
          "Snpun-nnorm-Msoft-IOactive/60",
          "Snpun-nnorm-Msoft-IOactive/80",
          "Spun-norm-Mhard-IOinactive/0",
          "Spun-norm-Mhard-IOinactive/20",
          "Spun-norm-Mhard-IOinactive/40",
          "Spun-norm-Mhard-IOinactive/50",
          "Spun-norm-Mhard-IOinactive/60",
          "Spun-norm-Mhard-IOinactive/80",
          "Spun-norm-Mhard-IOactive/0",
          "Spun-norm-Mhard-IOactive/20",
          "Spun-norm-Mhard-IOactive/40",
          "Spun-norm-Mhard-IOactive/50",
          "Spun-norm-Mhard-IOactive/60",
          "Spun-norm-Mhard-IOactive/80",
          "Spun-norm-Msoft-IOinactive/0",
          "Spun-norm-Msoft-IOinactive/20",
          "Spun-norm-Msoft-IOinactive/40",
          "Spun-norm-Msoft-IOinactive/50",
          "Spun-norm-Msoft-IOinactive/60",
          "Spun-norm-Msoft-IOinactive/80",
          "Spun-norm-Msoft-IOactive/0",
          "Spun-norm-Msoft-IOactive/20",
          "Spun-norm-Msoft-IOactive/40",
          "Spun-norm-Msoft-IOactive/50",
          "Spun-norm-Msoft-IOactive/60",
          "Spun-norm-Msoft-IOactive/80",
          "Spun-nnorm-Mhard-IOinactive/0",
          "Spun-nnorm-Mhard-IOinactive/20",
          "Spun-nnorm-Mhard-IOinactive/40",
          "Spun-nnorm-Mhard-IOinactive/50",
          "Spun-nnorm-Mhard-IOinactive/60",
          "Spun-nnorm-Mhard-IOinactive/80",
          "Spun-nnorm-Mhard-IOactive/0",
          "Spun-nnorm-Mhard-IOactive/20",
          "Spun-nnorm-Mhard-IOactive/40",
          "Spun-nnorm-Mhard-IOactive/50",
          "Spun-nnorm-Mhard-IOactive/60",
          "Spun-nnorm-Mhard-IOactive/80",
          "Spun-nnorm-Msoft-IOinactive/0",
          "Spun-nnorm-Msoft-IOinactive/20",
          "Spun-nnorm-Msoft-IOinactive/40",
          "Spun-nnorm-Msoft-IOinactive/50",
          "Spun-nnorm-Msoft-IOinactive/60",
          "Spun-nnorm-Msoft-IOinactive/80",
          "Spun-nnorm-Msoft-IOactive/0",
          "Spun-nnorm-Msoft-IOactive/20",
          "Spun-nnorm-Msoft-IOactive/40",
          "Spun-nnorm-Msoft-IOactive/50",
          "Spun-nnorm-Msoft-IOactive/60",
          "Spun-nnorm-Msoft-IOactive/80")

y <- c(0.65,0.85,0.78,0.82,as.array(propPaid))
x <- c(0.70,0.75,0.74,0.85,as.array(1 - (propDenExt + propDenPun)))

data <- data.table(cbind(city,x,y))

ggplot(data, aes(x=as.numeric(as.character(x))*100, y=as.numeric(as.character(y))*100)) +
  xlim(0,100) + ylim(0,100) +
  xlab('% Unreported Cases') + ylab('% Completed Extortions') +
  geom_point(aes(color=as.character(city)), size=6) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))