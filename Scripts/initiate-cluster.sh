#!/bin/bash

echo  -e "\e[42mCreating resource directory \e"
mkdir -p hadoop/resources
cd hadoop/resources

echo  -e "\e[42mUpdating Repository for Java PPA \e"
sudo apt-add-repository ppa:webupd8team/java

echo  -e "\e[42mUpdating apt-get \e"
sudo apt-get update

echo  -e "\e[42mInstalling Java 7 \e"
sudo apt-get install oracle-java7-installer

echo -e "\e[42mCreating a new user for Hadoop"
sudo /usr/sbin/useradd hadoop

echo -e "\e[42mStarting installation of Hadoop"
cd /usr/local

echo -e "\e[42mDownloading Hadoop distribution"
wget http://mirrors.ibiblio.org/apache/hadoop/common/hadoop-1.2.1/hadoop-1.2.1-bin.tar.gz

echo -e "\e[42mUnpacking Hadoop distribution"
sudo tar xzf hadoop-1.2.1-bin.tar.gz
sudo chown -R hadoop:hadoop hadoop-1.2.1

echo -e "\e[42mMoving Hadoop tar file to resources"
mv hadoop-1.2.1-bin.tar.gz ~/hadoop/resources

