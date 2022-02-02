package main

import (
	"bytes"
	"crypto/cipher"
	"crypto/des"
	"os"
)

const KEY_3DES string = "philosophy912"

/*
   判断文件或文件夹是否存在
   如果返回的错误为nil,说明文件或文件夹存在
   如果返回的错误类型使用os.IsNotExist()判断为true,说明文件或文件夹不存在
   如果返回的错误为其它类型,则不确定是否在存在
*/
func CheckFile(path string) bool {
	_, err := os.Stat((path))
	if err == nil {
		return true
	}
	if os.IsNotExist(err) {
		return false
	}
	return false
}

// 加密
func Encrypt(str string) string {
	originData := []byte(str)
	byte_key := []byte(KEY_3DES)
	// 获取block块
	block, _ := des.NewTripleDESCipher(byte_key)
	// 补码
	originData = pkcPadding(originData, block.BlockSize())
	// 设置加密方式为 3DES  使用3条56位的密钥对数据进行三次加密
	blockMode := cipher.NewCBCEncrypter(block, byte_key[:8])
	// 创建明文长度的数组
	crypted := make([]byte, len(originData))
	// 加密明文
	blockMode.CryptBlocks(crypted, originData)
	return string(crypted[:])
}

// 补码
func pkcPadding(originData []byte, blockSize int) []byte {
	//计算需要补几位数
	padding := blockSize - len(originData)%blockSize
	//在切片后面追加char数量的byte(char)
	padtext := bytes.Repeat([]byte{byte(padding)}, padding)
	return append(originData, padtext...)
}
