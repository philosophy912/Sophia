set TEST_CASE=

set CURRENT_PATH=%cd%
set ALLURE_REEPORT=%CURRENT_PATH%\result\report\allure_report
set ALLURE_RESULTS=%CURRENT_PATH%\result\report\allure-results
if "%TEST_CASE%" == "" (set RESULT_FOLDER=%CURRENT_PATH%) else (set RESULT_FOLDER=%CURRENT_PATH%\%TEST_CASE%)
pytest -s --alluredir=%ALLURE_RESULTS% --clean-alluredir %RESULT_FOLDER%
allure generate %ALLURE_RESULTS% -c -o %ALLURE_REEPORT%
pause