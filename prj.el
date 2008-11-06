;; (set-variable 'jde-global-classpath (list
;;                                      (expand-file-name "./bin/prod")
;;                                      (expand-file-name "./bin/test")
;;                                      (expand-file-name "./lib/junit-4.5.jar")))
(set-variable 'jde-global-classpath '("./bin/prod"
                                      "./bin/test"
                                      "./lib/junit-4.5.jar"))

;;(setq jdibug-connect-host "localhost"
;;      jdibug-connect-port 5005)

(message "Loaded prj.el")
