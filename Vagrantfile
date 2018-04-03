Vagrant.configure(2) do |config|
# for production use CernVM 4
  config.vm.box = "westlife-eu/cernvm4"
# for development and testing use SL7  
#  config.vm.box = "westlife-eu/scientific_7_x86_64_minimal_gui"

  if Vagrant::VERSION =~ /^1.9.3/
    puts "vagrant version 1.9.3, fixing host_ip configuration "
    # forward standard web
    config.vm.network "forwarded_port", host_ip: "127.0.0.1", guest: 80, host: 8080
  else
    if Vagrant::VERSION =~ /^1.9.4/
      puts "vagrant version 1.9.4 detected. Upgrade to version 1.9.5+ or downgrade to version 1.9.3 or bellow"
      exit
    else
      puts "vagrant version:"
      puts Vagrant::VERSION
      # forward standard web
      config.vm.network "forwarded_port", guest: 80, host: 8080
    end
  end  
  config.vm.provider "virtualbox" do |vb|
  #   Display the VirtualBox GUI when booting the machine
    vb.gui = false
  #   Customize the amount of memory on the VM, 2GB enough for deployment, 4GB recommended for development:
    vb.memory = "2048"
    vb.cpus = "2"
    vb.customize ["modifyvm", :id, "--vram", "16"]
  end
  config.vm.synced_folder ".", "/vagrant", nfs: false  
  config.vm.provision "shell",  path: "bootstrap.sh"

  #config.vm.synced_folder ".", "/opt/shared"
  config.vm.synced_folder "../../", "/vagrant_data"
end
