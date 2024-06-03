## Repository Release Log

#### Version: 0.1

- Update Date: 2018-06-04
- Update Content:
  1. Initial release of common-util.

#### Version: 0.2

- Update Date: 2018-06-09
- Update Content:
  1. Added AESUtil and MD6 encryption tools with corresponding documentation.
  2. Modified Xss security interception to allow custom error message handling in the project.

#### Version: 0.3

- Update Date: 2018-06-26
- Update Content:
  1. Added OkHttpUtil utility class.
  2. Modified AESUtil encryption tool.
  3. Changed the parameter order for NIO file writing in FileUtil.

#### Version: 1.0

- Update Date: 2018-07-07
- Update Content:
  1. Updated validateUtil for mobile phone verification.

#### Version: 1.1

- Update Date: 2018-07-14
- Update Content:
  1. Updated RandomUtil for random number generation.

#### Version: 1.1

- Update Date: 2018-07-14
- Update Content:
  1. Updated RandomUtil for random number generation.

#### Version: 1.2

- Update Date: 2018-08-04
- Update Content:
  1. Upgraded common-util to version 1.2, resolving API-doc random value generation.

#### Version: 1.3

- Update Date: 2018-09-29
- Update Content:
  1. Upgraded common-util to version 1.3, added IP whitelist and blacklist interception.

#### Version: 1.4

- Update Date: 2018-09-29
- Update Content:
  1. Updated RandomUtil for random number generation.
  2. Added UUIDUtil utility class.
  3. ValidateUtil now includes HH:mm:ss format validation.

#### Version: 1.5

- Update Date: 2018-11-29
- Update Content:
  1. Optimized AES encryption tool.

#### Version: 1.6

- Update Date: 2018-12-11
- Update Content:
  1. Updated RandomUtil to generate LocalDate and LocalDateTime strings for smart-doc support.

#### Version: 1.7

- Update Date: 2018-12-13
- Update Content:
  1. Modified common-util module's CommonResult for compatibility with previous versions, added common static methods for rapid coding.

#### Version: 1.7.1

- Update Date: 2018-12-21
- Update Content:
  1. Modified common-util module to revise ValidateUtil email validation according to RFC 5322.
  2. Modified common-util module's DateTimeUtil to use JDK 8 features, following RFC 5322.

#### Version: 1.7.2

- Update Date: 2019-01-06
- Update Content:
  1. Modified common-util's StringUtil to add hyphen-to-camel-case conversion method.

#### Version: 1.8

- Update Date: 2019-01-18
- Update Content:
  1. Added Base64Util to common-util.
  2. Optimized CollectionUtil.
  3. Added HexUtil utility class.
  4. Optimized DateUtil.

#### Version: 1.8.2

- Update Date: 2019-03-18
- Update Content:
  1. Added HTTPS support to OkHttp tool.
  4. Added getData method to CommonResult for returning data.


#### Version: 1.9.1

- Update Date: 2020-03-25
- Update Details:
  1. Changed the default character set for reading text content in FileUtil to UTF-8
     This update ensures that FileUtil now reads files assuming UTF-8 encoding by default, enhancing compatibility with texts containing non-ASCII characters.

#### Version: 1.9.3

- Update Date: 2020-04-18
- Update Details:
  1. Added a method to check for Chinese characters in ValidateUtil
     A new function has been introduced in ValidateUtil to verify if a given string includes Chinese characters, catering to validation requirements in applications dealing with multilingual inputs.
