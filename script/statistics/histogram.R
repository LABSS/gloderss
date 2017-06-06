##
## Libraries
##
library(ggplot2)
library(data.table)


##
##
##
base <- "/data/workspace/gloders/gloderss/output/data/tus-100000"
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/tus-100000"

dir <- c("iw05-nw05-weak-noNorm-violent-low-inactive",
         "iw05-nw05-weak-noNorm-hidden-low-inactive")

content <- c(1, 2)
order <- c("violent", "hidden")
fills <- c("black", "black")
lines <- c(1, 2)


##
## Upload
##
replicas <- 0:9

simLen <- 100000
delta <- 100
con <- 200
ent <- 100

compensation <- NULL
consumer <- NULL
entrepreneur <- NULL
extortion <- NULL
io <- NULL
mafia <- NULL
mafiosi <- NULL
normative <- NULL
purchase <- NULL
state <- NULL

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
}

for(i in dirs){
  
##
## SALIENCE HISTOGRAM
##

##
## Pay Pizzo
##
png(filename=paste0(base,dir[i],"/histSalPayExt.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=saliencePayExtortion)) +
  xlim(0,1) + ylim(0,100) +
  ylab('Number of Entrepreneurs') + xlab('Norm salience of \n \'Pay pizzo\' ') +
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
## Do Not Pay Pizzo
##
png(filename=paste0(base,dir[i],"/histSalNotPayExt.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceNotPayExtortion)) +
  xlim(0,1) + ylim(0,100) +
  ylab('Number of Entrepreneurs') + xlab('Norm salience of \n \'Do not pay pizzo\' ') +
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
## Report
##
png(filename=paste0(base,dir[i],"/histSalRep.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceDenounce)) +
  xlim(0,1) + ylim(0,100) +
  ylab('Number of Entrepreneurs') + xlab('Norm salience of \n \'Report\' ') +
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
## Do Not Report
##
png(filename=paste0(base,dir[i],"/histSalNotRep.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceNotDenounce)) +
  xlim(0,1) + ylim(0,100) +
  ylab('Number of Entrepreneurs') + xlab('Norm salience of \n \'Do not report\' ') +
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

}