library(ggplot2)
library(data.table)
##
## Directory
##
base <- "/data/workspace/gloders/gloderss/output"


##
## All combinations
##
citizens <- c("iw03-nw07",
              "iw05-nw05")

states <- c("strong-norm",
            "weak-norm",
            "strong-noNorm",
            "weak-noNorm")

mafias <- c("violent-low",
            "violent-medium",
            "violent-high",
            "hidden-low",
            "hidden-medium",
            "hidden-high")

ios <- c("active",
         "inactive")

comb <- expand.grid(citizens,states,mafias,ios)
dir <- NULL
for(i in 1:nrow(comb)){
  dir <- c(dir, paste0(as.character(comb[i,1]),"-",
                       as.character(comb[i,2]),"-",
                       as.character(comb[i,3]),"-",
                       as.character(comb[i,4])))
}


##
## Research Questions
##
#
# Q1. Can the state do anything against a hidden and beneficial mafia?
#
base <- "/data/workspace/gloders/gloderss/output/data"
baseOutput <- "/data/workspace/gloders/gloderss/output/article/figures/Q1"

dir <- c("iw05-nw05-weak-noNorm-hidden-high-inactive",
         "iw05-nw05-strong-noNorm-hidden-high-inactive",
         "iw05-nw05-strong-norm-hidden-high-inactive",
         "iw05-nw05-strong-noNorm-hidden-high-active",
         "iw05-nw05-strong-norm-hidden-high-active")

content <- c(1,2,3,4,5)
order <- c("W-nN-H-H-I", "S-nN-H-H-I", "S-N-H-H-I", "S-nN-H-H-A", "S-N-H-H-A")
shapes <- c(15,16,17,18,19)
sizes <- c(10,10,10,10,10)
fills <- c("black","black","black","black","black")
lines <- c(1,2,3,4,5)

#
# Q2. Can the state do anything against a hidden and not beneficial mafia?
#
base <- "/data/workspace/gloders/gloderss/output/data"
baseOutput <- "/data/workspace/gloders/gloderss/output/article/figures/Q2"

dir <- c("iw05-nw05-weak-noNorm-hidden-low-inactive",
         "iw05-nw05-strong-noNorm-hidden-low-inactive",
         "iw05-nw05-strong-norm-hidden-low-inactive",
         "iw05-nw05-strong-noNorm-hidden-low-active",
         "iw05-nw05-strong-norm-hidden-low-active")

content <- c(1,2,3,4,5)
order <- c("W-nN-H-L-I", "S-nN-H-L-I", "S-N-H-L-I", "S-nN-H-L-A", "S-N-H-L-A")
shapes <- c(15,16,17,18,19)
sizes <- c(10,10,10,10,10)
fills <- c("black","black","black","black","black")
lines <- c(1,2,3,4,5)

#
# Q3. Can the state do anything against a violent and beneficial mafia?
#
base <- "/data/workspace/gloders/gloderss/output/data"
baseOutput <- "/data/workspace/gloders/gloderss/output/article/figures/Q3"

dir <- c("iw05-nw05-weak-noNorm-violent-high-inactive",
         "iw05-nw05-strong-noNorm-violent-high-inactive",
         "iw05-nw05-strong-norm-violent-high-inactive",
         "iw05-nw05-strong-noNorm-violent-high-active",
         "iw05-nw05-strong-norm-violent-high-active")

content <- c(1,2,3,4,5)
order <- c("W-nN-V-H-I", "S-nN-V-H-I", "S-N-V-H-I", "S-nN-V-H-A", "S-N-V-H-A")
shapes <- c(15,16,17,18,19)
sizes <- c(10,10,10,10,10)
fills <- c("black","black","black","black","black")
lines <- c(1,2,3,4,5)

#
# Q4. Can the state do anything against a violent and not beneficial mafia?
#
base <- "/data/workspace/gloders/gloderss/output/data"
baseOutput <- "/data/workspace/gloders/gloderss/output/article/figures/Q4"

dir <- c("iw05-nw05-weak-noNorm-violent-low-inactive",
         "iw05-nw05-strong-noNorm-violent-low-inactive",
         "iw05-nw05-strong-norm-violent-low-inactive",
         "iw05-nw05-strong-noNorm-violent-low-active",
         "iw05-nw05-strong-norm-violent-low-active")

content <- c(1,2,3,4,5)
order <- c("W-nN-V-L-I", "S-nN-V-L-I", "S-N-V-L-I", "S-nN-V-L-A", "S-N-V-L-A")
shapes <- c(15,16,17,18,19)
sizes <- c(10,10,10,10,10)
fills <- c("black","black","black","black","black")
lines <- c(1,2,3,4,5)


##
## Upload
##
replicas <- 0:9

simLen <- 10000
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
  
  if (!is.null(entrepreneur[[i]])) {
    nAffiliated[[i]] <- nrow(subset(entrepreneur[[i]], affiliated == "true")) / nReplicas
  } else {
    nAffiliated[[i]] <- 0
  }
  
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
## Number of Extortions
##
ne <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    ne[replica+1,i] <- nrow(subset(extortion[[i]], r == replica))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], ne[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","numExt"))
png(filename=paste0(baseOutput,"/numExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(numExt)),
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('Number of Extortions') + ylim(0,35000) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')
dev.off()


##
## Number of Paid of Extortion
##
data <- data.table(cbind(order,dir[content],nPaid[content]))
setnames(data,c("V2","V3"),c("dir","nPaid"))
png(filename=paste0(baseOutput,"/numPaidExt.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nPaid)))) +
  xlab('') + ylab('Number of Paid Extortions') + ylim(0,40000) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
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
## Number of Denounces
##
data <- data.table(cbind(order,dir[content],nDen[content]))
setnames(data,c("V2","V3"),c("dir","nDen"))
png(filename=paste0(baseOutput,"/numDenounce.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nDen)))) +
  xlab('') + ylab('Number of Denounces') + ylim(0,3000) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
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
## Number of Punishment
##
data <- data.table(cbind(order,dir[content],nPun[content]))
setnames(data,c("V2","V3"),c("dir","nPun"))
png(filename=paste0(baseOutput,"/numPun.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nPun)))) +
  xlab('') + ylab('Number of Punishments') + ylim(0,25000) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
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
dev.off()


##
## Number of Mafiosi Imprisoned
##
data <- NULL
nImp <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataM <- data.table(mafia[[i]])
  
  imp <- NULL
  for(t in time){
    imp <- rbind(imp, nrow(dataM[which(time == t & imprisoned == 'true'),]) / nReplicas)
  }
  
  nImp <- rbind(nImp, cbind(time, order[p], imp))
  p = p + 1
}
nImp <- data.table(nImp)
setnames(nImp, c("time", "V2", "V3"),
         c("time", "treatment", "imp"))

png(filename=paste0(baseOutput,"/numImprisoned.png"), width=1024, height=768)
ggplot(nImp, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(imp)),
                 group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,20) +
  xlab('Time Units') + ylab('Number of Mafiosi in Prison') +
  geom_line(size=2) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Proportion of Paid Extortions
##
pe <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    pe[replica+1,i] <- nrow(subset(extortion[[i]], (paid == "true" & r == replica))) /
      nrow(subset(extortion[[i]], r == replica))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pe[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","paidExt"))
png(filename=paste0(baseOutput,"/propPaidExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(paidExt))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% of Paid Extortion') + ylim(0,100) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')
dev.off()

png(filename=paste0(baseOutput,"/propPaidExt-50-85.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(paidExt))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% of Paid Extortion') + ylim(50,85) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')
dev.off()


##
## Proportion of Denunciation
##
pd <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    nDenExt <- nrow(subset(extortion[[i]], (paid == "false" &
                                              denouncedExtortion == "true" &
                                              r == replica)))
    
    nDenPun <- nrow(subset(extortion[[i]], (paid == "false" &
                                              denouncedPunishment == "true" &
                                              r == replica)))
    
    nExtort <- nrow(subset(extortion[[i]], r == replica))
    
    nNPaid <- nrow(subset(extortion[[i]], (paid == "false" & r == replica)))
    
    propDenExt <- nDenExt / nExtort
    
    propDenPun <- nDenPun / nNPaid
    
    pd[replica+1,i] <-  propDenExt + propDenPun
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pd[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","propDen"))
png(filename=paste0(baseOutput,"/propDenExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propDen))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% Denunciation') + ylim(0,100) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')
dev.off()

png(filename=paste0(baseOutput,"/propDenExt-0-25.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propDen))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% Denunciation') + ylim(0,25) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')
dev.off()


##
## Proportion of Punishment general
##
data <- data.table(cbind(order,dir[content],propPun[content]))
setnames(data,c("V2","V3"),c("dir","propPun"))
png(filename=paste0(baseOutput,"/propPun.png"), width=1024, height=768)
ggplot(data, aes(x=order,y=as.numeric(as.character(propPun))*100)) +
  xlab('Scenarios') + ylab('% Punishments') + ylim(0,100) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.position = 'none')
dev.off()


##
##  Proportion of Punishment per No Payment
##
data <- data.table(cbind(order,dir[content],propPunNPay[content]))
setnames(data,c("V2","V3"),c("dir","propPunNPay"))
png(filename=paste0(baseOutput,"/propPunNPay.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propPunNPay))*100)) +
  xlab('Scenarios') + ylab('% Punishments per No Payment') + ylim(0,100) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.position = 'none')
dev.off()


##
## Proportion of Investigation Leading to Imprisonment
##
pc <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    nExtort <- nrow(subset(extortion[[i]], r == replica))
    
    nInvCon <- nrow(subset(extortion[[i]], (paid == "false" &
                                              (investigatedExtortion == "true" |
                                                 investigatedPunishment == "true") &
                                              mafiosoConvicted == "true" &
                                              r == replica)))
    
    pc[replica+1,i] <-  nInvCon / nExtort
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pc[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","propConv"))
png(filename=paste0(baseOutput,"/propImprisonment.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propConv))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% Investigations Leading\n to Imprisonment') + ylim(0,100) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')
dev.off()


##
## Pay Extortion
##
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){  
    sal <- c(dataE[which(time == t),]$saliencePayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))
png(filename=paste0(baseOutput,"/salPayExt.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV))*100,
                     group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,100) +
  xlab('Time Units') + ylab('\'Pay Extortion\'\n Norm Salience') +
  geom_line(size=1) +
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
dev.off()

##
## Not Pay Extortion
##
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceNotPayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))
png(filename=paste0(baseOutput,"/salNotPayExt.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV))*100,
                     group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,100) +
  xlab('Time Units') + ylab('\'Do Not Pay Extortion\'\n Norm Salience') +
  geom_line(size=1) +
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
dev.off()

##
## Denounce Extortion
##
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))
png(filename=paste0(baseOutput,"/salDenExt.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV))*100,
                     group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,100) +
  xlab('Time Units') + ylab('\'Denounce Extortion\'\n Norm Salience') +
  geom_line(size=1) +
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
dev.off()

##
## Not Denounce Extortion
##
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceNotDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))
png(filename=paste0(baseOutput,"/salNotDenExt.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV))*100,
                     group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,100) +
  xlab('Time Units') + ylab('\'Do Not Denounce Extortion\'\n Norm Salience') +
  geom_line(size=1) +
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
dev.off()

##
## Graphics Reputation
##

##
## State Finder
##
data <- NULL
reputation <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$reputationStateFinder)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  reputation <- rbind(reputation, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
reputation <- data.table(reputation)
setnames(reputation, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(baseOutput,"/repStateFinder.png"), width=1024, height=768)
ggplot(reputation, aes(x=as.numeric(as.character(time)),
                       y=as.numeric(as.character(mV))*100,
                       group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,100) +
  xlab('Time Units') + ylab('Reputation State Finder') +
  geom_line(size=2) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        # legend.position = 'none',
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=36, face="bold"))
dev.off()

##
## State Protector
##
data <- NULL
reputation <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$reputationStateProtector)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  reputation <- rbind(reputation, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
reputation <- data.table(reputation)
setnames(reputation, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(baseOutput,"/repStateProtector.png"), width=1024, height=768)
ggplot(reputation, aes(x=as.numeric(as.character(time)),
                       y=as.numeric(as.character(mV))*100,
                       group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,100) +
  xlab('Time Units') + ylab('Reputation State Protector') +
  geom_line(size=2) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        # legend.position = 'none',
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=36, face="bold"))
dev.off()

##
## Mafia Punisher
##
data <- NULL
reputation <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$reputationMafiaPunisher)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  reputation <- rbind(reputation, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
reputation <- data.table(reputation)
setnames(reputation, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(baseOutput,"/repMafiaPunisher.png"), width=1024, height=768)
ggplot(reputation, aes(x=as.numeric(as.character(time)),
                       y=as.numeric(as.character(mV))*100,
                       group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,100) +
  xlab('Time Units') + ylab('Reputation Mafia Punisher') +
  geom_line(size=2) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        # legend.position = 'none',
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Entreprenur Pay Decision
##
i <- 3
line <- sample(nrow(extortion[[i]]),1)
e <- extortion[[i]][line]$entrepreneurId
re <- extortion[[i]][line]$r

dataE <- data.table(extortion[[i]][which(entrepreneurId == e & r == re),])

atanPayIG <- (0.5 * atan2(0.01 * dataE$payIndividual, 1) / (pi / 2.0)) + 0.5
atanNPayIG <- (0.5 * atan2(0.01 * dataE$notPayIndividual, 1) / (pi / 2.0)) + 0.5

data <- NULL
data <- cbind(data, "salPay", dataE$time, dataE$paySalience)
data <- rbind(data, cbind("salNotPay", dataE$time, dataE$notPaySalience))
data <- rbind(data, cbind("id", dataE$time, (atanPayIG / (atanPayIG + atanNPayIG))))
data <- data.table(data)
setnames(data, c("V1", "V2", "V3"), c("var", "time", "val"))

ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(val)),
                 group=var, colour=as.factor(var))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time') + ylab('Value') +
  geom_line(size=2) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=36, face="bold"))


##
## Entreprenur Denounce Decision
##
#i <- 1
#line <- sample(nrow(extortion[[i]]),1)
#e <- extortion[[i]][line]$entrepreneurId
#re <- extortion[[i]][line]$r

dataE <- data.table(extortion[[i]][which(entrepreneurId == e & r == re),])

data <- NULL
data <- cbind(data, "salDen", dataE$time, dataE$denounceSalience)
data <- rbind(data, cbind("salNotDen", dataE$time, dataE$notDenounceSalience))
data <- rbind(data, cbind("id", dataE$time, dataE$denounceIndividual))
data <- data.table(data)
setnames(data, c("V1", "V2", "V3"), c("var", "time", "val"))

ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(val)),
                 group=var, colour=as.factor(var))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time') + ylab('Value') +
  geom_line(size=2) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=36, face="bold"))