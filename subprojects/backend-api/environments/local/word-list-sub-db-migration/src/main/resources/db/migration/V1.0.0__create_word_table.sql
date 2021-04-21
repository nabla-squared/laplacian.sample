
/**
 * The table of words.
 */
CREATE TABLE t_word (
    `lemma` VARCHAR(200) NOT NULL,
    `rank` INTEGER NOT NULL,
    `frequency` INTEGER NOT NULL,
    `japanese` VARCHAR(200) NOT NULL,
    `commentary` VARCHAR(200) NOT NULL,
    PRIMARY KEY (lemma)
);


