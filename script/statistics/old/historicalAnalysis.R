# 0 innovators
content <- c(1,49,61,67,43)
content <- c(1,2,3,4,5)
order <- c('S1','S2','S3','S4','S5')
shapes <- c(15,16,17,18,19)
sizes <- c(10,10,10,10,10)
fills <- c("black","black","black","black","black")
lines <- c(1,2,3,4,6)

# Single run
content <- c(1,2)
order <- c('S1','S2')
shapes <- c(15,16)
sizes <- c(10,10)
fills <- c("white","white")
lines <- c(1,2)

# S3 and S4
content <- c(1,2)
order <- c('P3','P4')
shapes <- c(15,16)
sizes <- c(10,10)
fills <- c("white","white")
lines <- c(1,2)

# S1s
content <- c(1,49,61,67)
content <- c(1,2,3,4)
order <- c("S1", "S1-IO", "S1-IO-Norm", "S1-Norm")
shapes <- c(15,16,17,18)
sizes <- c(10,10,10,10)
fills <- c("black","black","black","black")
lines <- c(1,2,3,4)

# S2s
content <- c(5,6,7)
order <- c("S2", "S2-IO", "S2-IO-Norm")
shapes <- c(15,16,17)
sizes <- c(10,10,10)
fills <- c("black","black","black")
lines <- c(1,2,3)

# S3s
content <- c(8,9,10,11)
order <- c("S3", "S3-IO-Weak", "S3-IO-Weak-Norm", "S3-Weak")
shapes <- c(15,16,17,18)
sizes <- c(10,10,10,10)
fills <- c("black","black","black","black")
lines <- c(1,2,3,4)

# Ss
content <- c(1,2,3,4,5,6,7,8,9,10,11,12,13)
order <- c("S1",
           "S1-IO",
           "S1-IO-Norm",
           "S1-Norm",
           "S2",
           "S2-IO",
           "S2-IO-Norm",
           "S3",
           "S3-IO-Weak",
           "S3-IO-Weak-Norm",
           "S3-Weak",
           "S4",
           "S5")
shapes <- c(15,16,17,18)
sizes <- c(10,10,10,10,10,10,10,10,10,10,10,10,10)
fills <- c("black","black","black","black","black","black","black","black",
           "black","black","black","black","black")

##
## Scatterplot of Extortions
##
data <- data.table(cbind(order,dir[content],nExtortion[content]))
setnames(data,c("V2","V3"),c("dir","nExtortion"))
png(filename=paste0(base,"/numExtortionH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nExtortion)))) +
  xlab('Scenarios') + ylab('Number of Extortions') + ylim(0,2500) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')
dev.off()

#legend.title = element_blank(),
#legend.text = element_text(colour="black", size=24, face="bold"))


##
## Scatterplot of Paid of Extortion
##
data <- data.table(cbind(order,dir[content],nPaid[content]))
setnames(data,c("V2","V3"),c("dir","nPaid"))
png(filename=paste0(base,"/numPaidExtH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nPaid)))) +
  xlab('') + ylab('Number of Paid Extortions') + ylim(0,100000) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
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
data <- data.table(cbind(order,dir[content],propPaid[content]))
setnames(data,c("V2","V3"),c("dir","propPaid"))
png(filename=paste0(base,"/propPaidExtH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propPaid))*100)) +
  xlab('Scenarios') + ylab('% Paid Extortions') + ylim(0,100) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
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
## Scatterplot of Proportion of Punishment general
##
data <- data.table(cbind(order,dir[content],propPun[content]))
setnames(data,c("V2","V3"),c("dir","propPun"))
png(filename=paste0(base,"/propPunH.png"), width=1024, height=768)
ggplot(data, aes(x=order,y=as.numeric(as.character(propPun))*100)) +
  xlab('Scenarios') + ylab('% Punishments') + ylim(0,100) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(aes(colour=as.character(order)), size=10) +
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
## Scatterplot of Proportion of Punishment per No Payment
##
data <- data.table(cbind(order,dir[content],propPunNPay[content]))
setnames(data,c("V2","V3"),c("dir","propPunNPay"))
png(filename=paste0(base,"/propPunNPayH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propPunNPay))*100)) +
  xlab('Scenarios') + ylab('% Punishments') + ylim(0,100) +
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
## Scatterplot of Number of Denounces
##
data <- data.table(cbind(order,dir[content],nDen[content]))
setnames(data,c("V2","V3"),c("dir","nDen"))
png(filename=paste0(base,"/numDenounceH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nDen)))) +
  xlab('') + ylab('Number of Denounces') + ylim(0,1600) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
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
data <- data.table(cbind(order,dir[content],propDenExt[content]))
setnames(data,c("V2","V3"),c("dir","propDenExt"))
png(filename=paste0(base,"/propDenExtH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propDenExt)))) +
  xlab('') + ylab('% Denounced Extortions') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
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
data <- data.table(cbind(order,dir[content],propDenPun[content]))
setnames(data,c("V2","V3"),c("dir","propDenPun"))
png(filename=paste0(base,"/propDenPunH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propDenPun)))) +
  xlab('') + ylab('% Denounces Punishments') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
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
data <- data.table(cbind(order,dir[content],propDen[content]))
setnames(data,c("V2","V3"),c("dir","propDen"))
png(filename=paste0(base,"/propDenH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propDen))*100)) +
  xlab('Scenarios') + ylab('% Denounces') + ylim(0,100) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  #geom_point(aes(shape=as.character(order)), size=10) +
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
## Scatterplot of Proportion of Imprisonment
##
data <- data.table(cbind(order,dir[content],nInvCon[content]/nExtortion[content]))
setnames(data,c("V2","V3"),c("dir","nInvCon"))
png(filename=paste0(base,"/propImprisonmentH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nInvCon))*100)) +
  xlab('Scenarios') + ylab('Proportion of Imprisonment') + ylim(0,100) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  #geom_point(aes(shape=as.character(order)), size=10) +
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
## Norms' set shift
##
n <- c(0,60,120,150,180,240)
nShift <- NULL
for(i in content){
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

  aux <- data.table(consumer[[i]])
  new <- new + nrow(aux[which((time == simLen) &
                          (saliencePayExtortion <= salienceNotPayExtortion) &
                          (salienceDenounce >= salienceNotDenounce)),])
  
  traditional <- traditional + nrow(aux[which((time == simLen) &
                                  (saliencePayExtortion > salienceNotPayExtortion) &
                                  (salienceDenounce < salienceNotDenounce)),])
  
  onlyNotPay <- onlyNotPay + nrow(aux[which((time == simLen) &
                                 (saliencePayExtortion <= salienceNotPayExtortion) &
                                 (salienceDenounce < salienceNotDenounce)),])
  
  onlyDenounce <- onlyDenounce + nrow(aux[which((time == simLen) &
                                   (saliencePayExtortion > salienceNotPayExtortion) &
                                   (salienceDenounce >= salienceNotDenounce)),])
  
  z <- i %% 6
  if(z == 0){
    z <- 6
  }
  iTrad <- ((ent+con)-n[z])
  nShift <- rbind(nShift, c(i, ((iTrad-traditional)/iTrad), onlyNotPay/iTrad,
                            onlyDenounce/iTrad, ((new-n[z])/iTrad)))
}

write.table(nShift, file=paste0(base,"/nShift.csv"),
            quote=FALSE, append=FALSE, sep=";", col.names=FALSE, row.names=FALSE)

##
## Validation Information
##
city <- c("Agrigento",
          "Catania",
          "Messina",
          "Palermo",
          "Siracusa",
          "Trapani",
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

y <- c(0.630,0.845,0.759,0.669,0.833,0.333,as.array(propPaid[content]))
x <- c(0.716,0.764,0.759,0.829,0.867,0.762,as.array(1 - (propDenExt[content] + propDenPun[content])))
#c <- city[c(1,2,3,4,(content+4))]
c <- c(city[c(1,2,3,4,5,6)],"","","","","")

data <- data.table(cbind(c("Agrigento","Catania","Messina","Palermo",
                           "Siracusa","Trapani",order),c,x,y))

png(filename=paste0(base,"/validationI.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(x))*100,
                 y=as.numeric(as.character(y))*100,
                 label=c)) +
  xlim(0,100) + ylim(0,100) +
  xlab('% Unreported Cases') + ylab('% Completed Extortions') +
  geom_point(aes(shape=as.character(c("Empirical","Empirical","Empirical",
                                      "Empirical","Empirical","Empirical",order))), size=6) +
  geom_text(hjust=0.3, vjust=1.6, size=6) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.background = element_blank())
dev.off()


##
## Graphics Salience
##

##
## Pay Extortion
##
data <- NULL
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$saliencePayExtortion,
             dataC[which(time == t),]$saliencePayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(base,"/salPayExtH.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.65) +
  xlab('Time Units') + ylab('\'Pay Extortion\' Norm Salience') +
  geom_line(size=2) +
  #coord_cartesian(ylim=c(0.25, 0.75)) + 
  #scale_y_continuous(breaks=seq(0.25, 0.75, 0.10)) +
  #geom_vline(aes(xintercept=10000),
  #           color="red", linetype="dashed", size=1.5) +
  #geom_vline(aes(xintercept=20000),
  #           color="red", linetype="dashed", size=1.5) +
  #geom_vline(aes(xintercept=30000),
  #           color="red", linetype="dashed", size=1.5) +
  #geom_vline(aes(xintercept=40000),
  #           color="red", linetype="dashed", size=1.5) +
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
## Not Pay Extortion
##
data <- NULL
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceNotPayExtortion,
             dataC[which(time == t),]$salienceNotPayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(base,"/salNotPayExtH.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.65) +
  xlab('Time Units') + ylab('\'Do Not Pay Extortion\' Norm Salience') +
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
## Denounce Extortion
##
data <- NULL
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceDenounce,
             dataC[which(time == t),]$salienceDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(base,"/salDenExtH.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.65) +
  xlab('Time Units') + ylab('\'Denounce Extortion\' Norm Salience') +
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
## Not Denounce Extortion
##
data <- NULL
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceNotDenounce,
             dataC[which(time == t),]$salienceNotDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(base,"/salNotDenExtH.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.65) +
  xlab('Time Units') + ylab('\'Do Not Denounce Extortion\' Norm Salience') +
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

png(filename=paste0(base,"/repStateFinder.png"), width=1024, height=768)
ggplot(reputation, aes(x=as.numeric(as.character(time)),
                       y=as.numeric(as.character(mV)),
                       group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
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

png(filename=paste0(base,"/repStateProtector.png"), width=1024, height=768)
ggplot(reputation, aes(x=as.numeric(as.character(time)),
                       y=as.numeric(as.character(mV)),
                       group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
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

png(filename=paste0(base,"/repMafiaPunisher.png"), width=1024, height=768)
ggplot(reputation, aes(x=as.numeric(as.character(time)),
                       y=as.numeric(as.character(mV)),
                       group=treatment, colour=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
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