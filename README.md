# Repository
Reference implementation of a repository that supplies suitable metadata to the portal. This is developed within work package WP6 by the [West-Life H2020 project](https://west-life.eu) running from 2015 to 2018. It provides application level services usable for structural biology use cases and follows [the structural biology data lifecycle](http://internal-wiki.west-life.eu/w/images/9/9c/Assessment_of_the_life_cycle_of_structural_data_and_comparison_with_other_scientific_data.docx). Data management work package WP6 build on existing infrastructure for storing and accessing data. Full documentation is rendered in [HTML docs](https://h2020-westlife-eu.gitbooks.io/virtual-folder-docs/content/) or [PDF docs](https://www.gitbook.com/download/pdf/book/h2020-westlife-eu/virtual-folder-docs)

# Test installation
If you don't have vagrant and/or virtualbox, download and install [vagrant tool](https://www.vagrantup.com/downloads.html) and [Virtualbox](https://www.virtualbox.org/wiki/Downloads). Then execute following in your command line:

    git clone https://github.com/h2020-westlife-eu/wp6-repository.git
    cd wp6-repository
    vagrant up
