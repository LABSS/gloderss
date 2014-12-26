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
nDenPun <- nrow(subset(extortion, (denouncedExtortion == "true" |
                                     denouncedPunishment == "true") &
                         mafiaPunished == "true"))

##
## Calculation
##
propPaid <- nPaid / nExtortion
propDen <- nDen / nNPaid
propInvExt <- nInvExt / nDen
propCus <- nInvExtCus / nDen
propCon <- nInvExtCon / nDen

##
## Proportion
##
prop <- cbind(propPaid, propDen, propInvExt, propCus, propCon)

##
## Write
##
write.table(prop, file=paste(base,filename,replica,"/analisys.csv", sep=""),
            append=FALSE,sep=";", col.names=TRUE, row.names=FALSE)