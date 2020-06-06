# Imageware

[Alphanewbie/Imageware](https://github.com/Alphanewbie/Imageware.git)

## 개요

exe파일을 png 이미지 파일로 위장시켜서 위장된 파일이 실행 되면,

지정된 범위에 있는 파일들을 전부 암호화 시킨다.

## **팀원 및 역활**

- 팀 대표 : 김교연
- 팀 원 : 김형래
- 팀 원 : 양준수
- 팀 원 : 이찬우

## 사용기술

- Java
    - [org.apache.commons.io](http://org.apache.commons.io/) 라이브러리 사용
- AES/ECB
- WinRAR
- HxD Editor

## 구조

![Imageware%20ab435064004c4adebc25cbde57abd16b/_.png](https://raw.githubusercontent.com/Alphanewbie/Imageware/master/READMEIMG/RelationshipDiagram.png)

## 의의

1. 현재는 winrar에서 패치가 되서 파일만 PNG로 보이고, 실행이 되지는 않는다.

    → 그러니까, 툴들의 업데이트를 꾸준히 해주는 것만으로 대부분의 보안 취약점은 막을 수 있다.

    악성 코드의 개발 자체는 어렵지 않으며 유포가 중요하다

2. RSA가 더 확실한 보안을 제공하지만, AES보단 훨씬 느리다.
    
    - 빠르게 파일들을 암호화 시켜야 되는 랜섬웨어에는 맞지 않는다.